package net.lostluma.server_stats.api.v1.player;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;

import java.util.Optional;

/**
 * View and modify both vanilla and modded statistics.
 * <br>
 * Unlike vanilla, values may be read or set as longs.
 * <br><br>
 * This interface is implemented on {@code PlayerEntity} via injected interfaces.
 * <br>
 * To view and modify offline players' statistics use {@code ServerPlayerStats.get} instead.
 */
public interface MutableStats extends DisplayStats {
	/**
	 * Reset a statistic to its initial value.
	 *
	 * @param stat The statistic to reset.
	 * @return The previous value obtained by the player.
	 * @throws IllegalStateException Statistics are no longer mutable (Context closed).
	 */
	default long reset(ServerStatistic stat) throws IllegalStateException {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Unlock an achievement.
	 * <br>
	 * Note: A player may only earn an achievement if the parent achievement is unlocked.
	 *
	 * @param achievement The achievement to unlock.
	 * @return Whether the achievement was unlocked.
	 * @throws IllegalStateException Statistics are no longer mutable (Context closed).
	 */
	default boolean unlock(ServerAchievement achievement) throws IllegalStateException {
		Optional<ServerAchievement> parent = achievement.parent();

		if (!parent.isPresent() || this.isUnlocked(parent.get())) {
			this.increment(achievement);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Increment a statistic's value by one.
	 *
	 * @param stat The statistic to modify.
	 * @return The player's previous value for this statistic.
	 * @throws IllegalStateException Statistics are no longer mutable (Context closed).
	 */
	default long increment(ServerStatistic stat) throws IllegalStateException {
		return this.increment(stat, 1);
	}

	/**
	 * Update a statistic's value by a specific amount.
	 *
	 * @param stat The statistic to modify.
	 * @param amount The amount of change. May be negative.
	 * @return The player's previous value for this statistic.
	 * @throws IllegalStateException Statistics are no longer mutable (Context closed).
	 */
	default long increment(ServerStatistic stat, long amount) throws IllegalStateException {
		throw new RuntimeException("Interface implementation missing!");
	}
}
