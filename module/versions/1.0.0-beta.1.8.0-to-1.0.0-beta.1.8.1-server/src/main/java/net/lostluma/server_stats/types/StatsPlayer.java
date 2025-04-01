package net.lostluma.server_stats.types;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stat;
import org.jetbrains.annotations.Nullable;

public interface StatsPlayer {
    public default @Nullable ServerPlayerStats server_stats$getStats() {
        throw new RuntimeException("No implementation for server_stats$getStats found.");
    }

    public default void server_stats$incrementStat(Stat stat, int amount) {
		throw new RuntimeException("No implementation for server_stats$incrementStat found.");
    }

    public default void server_stats$saveStats() {
        throw new RuntimeException("No implementation for server_stats$saveStats found.");
    }
}
