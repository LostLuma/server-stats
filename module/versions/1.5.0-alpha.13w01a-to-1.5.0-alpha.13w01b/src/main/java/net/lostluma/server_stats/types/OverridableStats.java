package net.lostluma.server_stats.types;

import net.lostluma.server_stats.stats.ServerPlayerStats;

import java.util.Map;

public interface OverridableStats {
    public default void player_stats$override(ServerPlayerStats override) {
        throw new RuntimeException("No implementation for player_stats$override found.");
    }

    public default void player_stats$override(Map<String, Integer> override) {
        throw new RuntimeException("No implementation for player_stats$override found.");
    }
}
