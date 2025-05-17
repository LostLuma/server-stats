package net.lostluma.server_stats.impl.ext.common;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Player extension that allows uniquely identifying the player.
 */
public interface Identifiable {
	/**
	 * The player's human-readable username.
	 */
	default @NotNull String server_stats$name() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * The player's globally-unique user id.
	 */
	default @NotNull UUID server_stats$identifier() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Set the player's globally-unique user id.
	 */
	default void server_stats$setIdentifier(@NotNull UUID identifier) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
