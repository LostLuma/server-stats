package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.error.NoContextAvailable;
import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.lostluma.server_stats.impl.player.DisplayStatsImpl;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.lostluma.server_stats.impl.service.ClientNetworking;
import net.lostluma.server_stats.impl.util.ResultImpl;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ClientPlayerStatsImpl {
	// Note: Only null before Minecraft class init
	// And on merged Minecraft versions (=>1.3.2).
	private static @Nullable Identifiable session;

	// Null when outside a world, otherwise always present
	private static @Nullable LocalDisplayStatsImpl playerStats;

	private static final Map<String, Consumer<Result<DisplayStats, String>>> nameRequests = new HashMap<>();
	private static final Map<UUID, Consumer<Result<DisplayStats, String>>> identifierRequests = new HashMap<>();

	public static void setInWorld(boolean value) {
		if (!value) {
			playerStats = null;
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
			}
		}
	}

	public static void setSession(Identifiable value) {
		session = value;
	}

	private static boolean isInWorld() {
		return playerStats != null;
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
		if (isInWorld()) {
			nameRequests.put(name, handler);
			ClientNetworking.INSTANCE.fetch(name);
		} else {
			handler.accept(ResultImpl.error("Currently not in a world."));
		}
	}

	public static void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		if (isInWorld()) {
			identifierRequests.put(identifier, handler);
			ClientNetworking.INSTANCE.fetch(identifier);
		} else {
			handler.accept(ResultImpl.error("Currently not in a world."));
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
