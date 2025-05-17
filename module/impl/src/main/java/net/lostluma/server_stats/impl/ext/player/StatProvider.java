package net.lostluma.server_stats.impl.ext.player;

import org.jetbrains.annotations.Nullable;

/**
 * Player extension which provides the persistent stats instance.
 */
public interface StatProvider {
	/**
	 * Get the current persistent stats instance, if available.
	 */
	default @Nullable PersistentStats server_stats$stats() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
