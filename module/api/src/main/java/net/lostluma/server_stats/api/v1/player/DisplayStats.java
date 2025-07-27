package net.lostluma.server_stats.api.v1.player;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import org.jetbrains.annotations.Contract;

/**
 * Read both vanilla and modded statistics.
 * <br>
 * Unlike vanilla, values may be read as longs.
 * <br><br>
 * Viewing the client player's stats may be done via {@code ClientPlayerStats.get}.
 * <br>
 * On versions with the vanilla statistic system, it's also implemented on {@code PlayerStats} via injected interfaces.
 * <br><br>
 * To view other players' statistics, use {@code ClientPlayerStats.fetch} instead.
 */
public interface DisplayStats {
	/**
	 * Read a statistic's current value.
	 *
	 * @param stat The statistic to retrieve.
	 * @return The current value obtained by the player.
	 */
	@Contract(pure = true)
	default long get(ServerStatistic stat) {
		throw new RuntimeException("Interface implementation missing!");
	}

	/**
	 * Read whether an achievement has been obtained.
	 *
	 * @param achievement The achievement to retrieve.
	 * @return Whether the player has obtained the achievement.
	 */
	@Contract(pure = true)
	default boolean isUnlocked(ServerAchievement achievement) {
		return this.get(achievement) > 0;
	}
}
