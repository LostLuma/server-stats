package net.lostluma.server_stats.utils;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stat;

public interface StatsPlayer {
    public default void server_stats$incrementStat(Stat stat, int amount) {
		throw new RuntimeException("No implementation for server_stats$incrementStat found.");
    }

	public default void server_stats$saveStats() {
		throw new RuntimeException("No implementation for server_stats$saveStats found.");
	}
}
