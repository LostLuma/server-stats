package net.lostluma.server_stats.api.v1.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.ApiProxy;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Utility for fetching other players' statistics on the client.
 */
@Environment(EnvType.CLIENT)
public interface ClientPlayerStats {
	/**
	 * Fetch another player's stats from the server.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param name The player's username.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	static void fetch(String name, Consumer<Result<DisplayStats, String>> handler) {
		ApiProxy.getInstance().fetch(name, handler);
	}

	/**
	 * Fetch another player's stats from the server.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param identifier The player's identifier.
	 * @param handler A callback receiving the statistics, or null, once the request is completed.
	 */
	static void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		ApiProxy.getInstance().fetch(identifier, handler);
	}
}
