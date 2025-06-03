package net.lostluma.server_stats.impl.ext.player;

import net.lostluma.server_stats.api.player.MutableStats;

import java.util.Map;

/**
 * Extended version of {@code MutableStats} that exposes underlying storage mechanisms.
 */
public interface PersistentStats extends MutableStats {
	default void server_stats$save() {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$close() {
		throw new RuntimeException("Interface implementation missing!");
	}

	default Map<String, Long> server_stats$values() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Serialize either only small, or large values.
	 * This is needed to ensure older Server Stats clients can receive data.
	 */
	default String server_stats$serialize(boolean large) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
