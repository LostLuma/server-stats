package net.lostluma.server_stats.api.v1.server;

import net.lostluma.server_stats.api.v1.player.MutableStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.ApiProxy;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Utility for fetching player statistics on the server.
 */
public interface ServerPlayerStats {
	/**
	 * Fetch a player's statistics. The player may be offline.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param name The player's username.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	static void fetch(String name, Consumer<Result<MutableStats, String>> handler) {
		ApiProxy.getInstance().fetchMutable(name, handler);
	}

	/**
	 * Fetch a player's statistics. The player may be offline.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param identifier The player's identifier.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	static void fetch(UUID identifier, Consumer<Result<MutableStats, String>> handler) {
		ApiProxy.getInstance().fetchMutable(identifier, handler);
	}
}
