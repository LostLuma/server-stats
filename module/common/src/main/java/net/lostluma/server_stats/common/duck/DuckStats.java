package net.lostluma.server_stats.common.duck;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;

import java.util.Map;

public interface DuckStats {
	default void player_stats$override(ServerPlayerStats override) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void player_stats$override(Map<String, Integer> override) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
