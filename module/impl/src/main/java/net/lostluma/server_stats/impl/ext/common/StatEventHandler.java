package net.lostluma.server_stats.impl.ext.common;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;

/**
 * Player extension which allows players to listen to stat updates.
 */
public interface StatEventHandler {
	/**
	 * Reset a statistic on the player's client.
	 */
	default void server_stats$reset(ServerStatistic stat) {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Push a stat update to the player's client.
	 */
	default void server_stats$push(ServerStatistic stat, long value) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
