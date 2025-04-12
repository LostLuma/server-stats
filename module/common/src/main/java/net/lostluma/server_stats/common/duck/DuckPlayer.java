package net.lostluma.server_stats.common.duck;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DuckPlayer {
	/**
	 * The player's human-readable username.
	 */
	default @NotNull String server_stats$name() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * The player's globally-unique user id.
	 */
	default @NotNull String server_stats$identifier() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Set the player's globally-unique user id.
	 */
	default void server_stats$setIdentifier(@NotNull String identifier) {
		throw new RuntimeException("Interface implementation missing!");
	}

	default @Nullable ServerPlayerStats server_stats$getStats() {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Persist the player's statistics on disk.
	 */
	default void server_stats$saveStats() {
		ServerPlayerStats stats = this.server_stats$getStats();

		if (stats != null) {
			stats.save();
		}
	}

	/**
	 * Increment a statistic.
	 */
	default long server_stats$incrementStat(@NotNull ServerStat stat, int amount) {
		ServerPlayerStats stats = this.server_stats$getStats();

		if (stats == null) {
			return 0L;
		} else {
			return stats.increment(stat, amount);
		}
	}

	/**
	 * Push a stat update to the player's client.
	 */
	default void server_stats$push(@NotNull ServerStat stat, long value) {
		throw new RuntimeException("Interface implementation missing!");
	}
}
