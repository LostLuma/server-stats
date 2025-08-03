package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.error.NoContextAvailable;
import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.lostluma.server_stats.impl.player.DisplayStatsImpl;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.lostluma.server_stats.impl.server.ServerPlayerStatsImpl;
import net.lostluma.server_stats.impl.service.ClientNetworking;
import net.lostluma.server_stats.impl.util.ResultImpl;
import net.lostluma.server_stats.util.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ClientPlayerStatsImpl {
	// Whether the current world is a singleplayer
	// World on a split Minecraft version (<1.3.2)
	private static boolean splitSingleplayerWorld;

	// Note: Only null before Minecraft class init
	// And on merged Minecraft versions (=>1.3.2).
	private static @Nullable Identifiable session;

	// Server Stats version, if v1.4+ is installed
	private static @Nullable String serverVersion;

	// Null when outside a world, otherwise always present
	private static @Nullable LocalDisplayStatsImpl playerStats;

	private static final Map<String, Consumer<Result<DisplayStats, String>>> nameRequests = new HashMap<>();
	private static final Map<UUID, Consumer<Result<DisplayStats, String>>> identifierRequests = new HashMap<>();

	public static void setInWorld(boolean value) {
		if (!value) {
			playerStats = null;
			serverVersion = null;
		} else {
			playerStats = new LocalDisplayStatsImpl(new HashMap<>(), session);

			// In singleplayer on versions before 1.3.2
			// The "server?" does not send packets when
			// Updating custom statistics. Instead, the
			// PlayerStatsCache or PersistentStatsProxy
			// Directly sends any update to the Display
			// Since it now implements StatEventHandler
			PlayerStatsCache cache = null;

			try {
				cache = PlayerStatsCache.getInstance();
			} catch (NoContextAvailable ignored) {
				// We're playing on a multiplayer world
				// Meaning we get the packets we expect
			}

			if (cache != null && session != null) {
				cache.get(playerStats);

				splitSingleplayerWorld = true;
				serverVersion = Constants.MOD_VERSION;
			} else {
				splitSingleplayerWorld = false;
			}
		}
	}

	public static void setServerVersion(String value) {
		serverVersion = value;
	}

	public static void setSession(Identifiable value) {
		session = value;
	}

	private static boolean isInWorld() {
		return playerStats != null;
	}

	private static boolean fetchSupported() {
		return serverVersion != null;
	}

	public static Result<DisplayStats, String> get() {
		if (isInWorld()) {
			return ResultImpl.ok(playerStats);
		} else {
			return ResultImpl.error("Player is not inside a world.");
		}
	}

	public static @Nullable LocalDisplayStatsImpl getPlayerStats() {
		return playerStats;
	}

	public static void fetch(String name, Consumer<Result<DisplayStats, String>> handler) {
		if (!isInWorld()) {
			handler.accept(ResultImpl.error("Currently not in a world."));
		} else if (!fetchSupported()) {
			handler.accept(ResultImpl.error("Server Stats 1.4 (or newer) not installed on server."));
		} else if (!splitSingleplayerWorld) {
			// Either a real server, or >=1.3.2
			// So we can send packets to the other side
			nameRequests.put(name, handler);
			ClientNetworking.INSTANCE.fetch(name);
		} else {
			// A singleplayer world on Minecraft <1.3.2
			ServerPlayerStatsImpl.fetch(name, result -> {
				// Can't just return the same Result here
				// Since Java generics can not downcast this
				if (result.isOk()) {
					handler.accept(ResultImpl.ok(result.value()));
				} else {
					handler.accept(ResultImpl.error(result.error()));
				}
			});
		}
	}

	public static void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		if (!isInWorld()) {
			handler.accept(ResultImpl.error("Currently not in a world."));
		} else if (!fetchSupported()) {
			handler.accept(ResultImpl.error("Server Stats 1.4 (or newer) not installed on server."));
		} else if (!splitSingleplayerWorld) {
			// Either a real server, or >=1.3.2
			// So we can send packets to the other side
			identifierRequests.put(identifier, handler);
			ClientNetworking.INSTANCE.fetch(identifier);
		} else {
			// A singleplayer world on Minecraft <1.3.2
			ServerPlayerStatsImpl.fetch(identifier, result -> {
				// Can't just return the same Result here
				// Since Java generics can not downcast this
				if (result.isOk()) {
					handler.accept(ResultImpl.ok(result.value()));
				} else {
					handler.accept(ResultImpl.error(result.error()));
				}
			});
		}
	}

	public static void onResponse(@Nullable String name, @Nullable UUID identifier, @Nullable Map<String, Long> values, @Nullable String error) {
		Result<DisplayStats, String> result;
		Consumer<Result<DisplayStats, String>> handler;

		if (values != null) {
			result = ResultImpl.ok(new DisplayStatsImpl(values));
		} else if (error != null) {
			result = ResultImpl.error(error);
		} else {
			throw new RuntimeException("unreachable");
		}

		handler = nameRequests.remove(name);

		if (handler != null) {
			handler.accept(result);
		}

		handler = identifierRequests.remove(identifier);

		if (handler != null) {
			handler.accept(result);
		}
	}
}
