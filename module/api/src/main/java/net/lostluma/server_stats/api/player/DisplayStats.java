package net.lostluma.server_stats.api.player;

import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;

/**
 * Read both vanilla and modded statistics.
 * <br>
 * Unlike vanilla, values may be read as longs.
 * <br><br>
 * This interface is implemented on {@code PlayerStats} via injected interfaces.
 * <br>
 * To view other players' statistics, use {@code ClientPlayerStats.fetch} instead.
 */
public interface DisplayStats {
	/**
	 * Read a statistic's current value.
	 *
	 * @param stat The statistic to retrieve.
	 * @return The current value obtained by the player.
	 */
	default long get(ServerStatistic stat) {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Read whether an achievement has been obtained.
	 *
	 * @param achievement The achievement to retrieve.
	 * @return Whether the player has obtained the achievement.
	 */
	default boolean isUnlocked(ServerAchievement achievement) {
		return this.get(achievement) > 0;
	}
}
