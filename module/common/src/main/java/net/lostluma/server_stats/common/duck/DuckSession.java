package net.lostluma.server_stats.common.duck;

import org.jetbrains.annotations.NotNull;

public interface DuckSession {
	/**
	 * The current player's globally-unique user id.
	 */
	default @NotNull String server_stats$identifier() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
