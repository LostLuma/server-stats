package net.lostluma.server_stats.common.duck;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;

import java.util.Collections;
import java.util.Map;

public interface DuckStats {
	default long server_stats$value(ServerStat stat) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$add(String key, long value) {
		this.server_stats$persist(Collections.singletonMap(key, value), false);
	}

	default void server_stats$replace(ServerPlayerStats override) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$persist(Map<String, Long> override, boolean clear) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
