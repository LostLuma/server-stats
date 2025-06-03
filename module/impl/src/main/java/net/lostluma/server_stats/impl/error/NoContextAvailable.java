package net.lostluma.server_stats.impl.error;

/**
 * Exception thrown when accessing the {@code PlayerStatsCache}
 * but there is no world context available to fulfill the request in.
 */
public class NoContextAvailable extends Error {
	public NoContextAvailable(String message) {
		super(message);
	}
}
