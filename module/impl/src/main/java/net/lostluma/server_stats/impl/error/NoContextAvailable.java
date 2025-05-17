package net.lostluma.server_stats.impl.error;

import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when accessing the {@code PlayerStatsCache}
 * but there is no world context available to fulfill the request in.
 */
public class NoContextAvailable extends Error {
	public NoContextAvailable(@NotNull String message) {
		super(message);
	}
}
