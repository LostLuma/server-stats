package net.lostluma.server_stats.api.server;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Utility for fetching on- and offline players' statistics on the server.
 */
public interface ServerPlayerStats {
	/**
	 * Get a player's statistics.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param name The player's username.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	static void get(@NotNull String name, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		ApiProxy.getInstance().get(name, handler);
	}

	/**
	 * Get a player's statistics.
	 * <br>
	 * Note: Callbacks passed to this method are accepted on the main thread.
	 *
	 * @param identifier The player's identifier.
	 * @param handler A callback receiving the statistics, or an error, once the request is completed.
	 */
	static void get(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		ApiProxy.getInstance().get(identifier, handler);
	}
}
