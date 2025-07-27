package net.lostluma.server_stats.api.v1.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonBlocking;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Utility for fetching player statistics on the client.
 */
@Environment(EnvType.CLIENT)
public interface ClientPlayerStats {
	/**
	 * Get the client player's stats in the current world.
	 *
	 * @return A view of the player's statistics, or an error, should no world be available.
	 */
	@Contract(pure = true)
	static Result<DisplayStats, String> get() {
		return ApiProxy.getInstance().getDisplay();
	}

	/**
	 * Fetch another player's stats from the server.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param name The player's username.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	@NonBlocking
	static void fetch(String name, Consumer<Result<DisplayStats, String>> handler) {
		ApiProxy.getInstance().fetchDisplay(name, handler);
	}

	/**
	 * Fetch another player's stats from the server.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param identifier The player's identifier.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	@NonBlocking
	static void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		ApiProxy.getInstance().fetchDisplay(identifier, handler);
	}
}
