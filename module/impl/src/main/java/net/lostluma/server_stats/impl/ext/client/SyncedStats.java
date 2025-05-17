package net.lostluma.server_stats.impl.ext.client;

import net.lostluma.server_stats.impl.ext.player.PersistentStats;

import java.util.Collections;
import java.util.Map;

/**
 * Extension of {@code PlayerStats} to allow overwriting its values more easily.
 */
public interface SyncedStats {
	default void server_stats$add(String key, long value) {
		this.server_stats$persist(Collections.singletonMap(key, value), false);
	}

	default void server_stats$reset(String key) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$replace(PersistentStats override) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$persist(Map<String, Long> override, boolean clear) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
