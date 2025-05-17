package net.lostluma.server_stats.impl.ext.common;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import org.jetbrains.annotations.NotNull;

/**
 * Player extension which allows players to listen to stat updates.
 */
public interface StatEventHandler {
	/**
	 * Reset a statistic on the player's client.
	 */
	default void server_stats$reset(@NotNull ServerStatistic stat) {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Push a stat update to the player's client.
	 */
	default void server_stats$push(@NotNull ServerStatistic stat, long value) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
