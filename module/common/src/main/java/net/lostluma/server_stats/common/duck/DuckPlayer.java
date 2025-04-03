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

	default @Nullable ServerPlayerStats server_stats$getStats() {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$saveStats() {
		ServerPlayerStats stats = this.server_stats$getStats();

		if (stats != null) {
			stats.save();
		}
	}

	default void server_stats$incrementStat(ServerStat stat, int amount) {
		ServerPlayerStats stats = this.server_stats$getStats();

		if (stats != null) {
			stats.increment(stat, amount);
		}
	}
}
