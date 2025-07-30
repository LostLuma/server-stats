package net.lostluma.server_stats.impl.server;

import net.lostluma.server_stats.impl.error.NoContextAvailable;
import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.player.PersistentStatsImpl;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatsCache {
	// Stats storage directory
	private final Path path;

	private final Map<UUID, PersistentStatsImpl> stats = new HashMap<>();

	private final ReferenceQueue<PersistentStatsProxy> queue = new ReferenceQueue<>();
	private final Map<PhantomReference<?>, UUID> references = new IdentityHashMap<>();
	private final Map<UUID, Integer> referenceCounts = new HashMap<>(); // Proxy count

	private static @Nullable PlayerStatsCache INSTANCE;

	private PlayerStatsCache(Path worldDir) {
		this.path = worldDir.resolve("stats");

		try {
			Files.createDirectories(this.path);
		} catch (IOException e) {
			throw new UncheckedIOException("Unable to create stats directory!", e);
		}
	}

	/**
	 * Create a new stats cache for a world.
	 */
	public static void newInstance(Path worldDir) {
		INSTANCE = new PlayerStatsCache(worldDir);
	}

	/**
	 * Get the current world's stats cache.
	 */
	public static PlayerStatsCache getInstance() {
		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			throw new NoContextAvailable("No world context available!");
		}
	}

	/**
	 * Close the current stats cache, if one exists.
	 */
	public static void closeInstance() {
		if (INSTANCE != null) {
			INSTANCE.close();
		}
	}

	public Path getPath() {
		return this.path;
	}

	public void save() {
		synchronized (this) {
			this.cleanup();

			for (PersistentStatsImpl impl : this.stats.values()) {
				impl.server_stats$save();
			}
		}
	}

	private void close() {
		synchronized (this) {
			this.save();

			for (PersistentStatsImpl impl : this.stats.values()) {
				impl.server_stats$close();
			}
		}

		INSTANCE = null;
	}

	public PersistentStats get(Identifiable player) {
		String username = player.server_stats$name();
		UUID identifier = player.server_stats$identifier();

		PersistentStats stats = this.get(username, identifier);

		// When getting stats for a ServerPlayerEntity or the local client
		// The class passed here should also be able to receive stat push events
		if (player instanceof StatEventHandler) {
			PersistentStatsImpl impl = this.stats.get(identifier);
			((StatEventHandlerProxy) impl.handler()).setParent((StatEventHandler) player);
		}

		return stats;
	}

	public PersistentStats get(String name, UUID identifier) {
		synchronized (this) {
			PersistentStatsImpl stats = this.stats.get(identifier);

			if (stats == null) {
				StatEventHandlerProxy proxy = new StatEventHandlerProxy();
				stats = new PersistentStatsImpl(name, identifier, proxy);
				this.stats.put(identifier, stats);
			}

			PersistentStatsProxy proxy = new PersistentStatsProxy(stats);
			this.references.put(new PhantomReference<>(proxy, this.queue), identifier);

			// There might be multiple references to the same underlying stats impl
			// We need to let the cleanup method know not to delete associated data
			this.referenceCounts.put(identifier, this.referenceCounts.getOrDefault(identifier, 0) + 1);

			return proxy;
		}
	}

	private void cleanup() {
		synchronized (this) {
			Reference<?> ref;

			while ((ref = this.queue.poll()) != null) {
				UUID identifier = this.references.remove(ref);
				int count = this.referenceCounts.get(identifier);

				if (count != 1) {
					// Other references for this UUID are still around.
					this.referenceCounts.put(identifier, count - 1);
				} else {
					// This was the last reference to a particular UUID
					PersistentStatsImpl stats = this.stats.remove(identifier);

					stats.server_stats$save();
					this.referenceCounts.remove(identifier);
				}
			}
		}
	}
}
