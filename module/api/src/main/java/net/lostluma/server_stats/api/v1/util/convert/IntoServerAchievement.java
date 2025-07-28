package net.lostluma.server_stats.api.v1.util.convert;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;

/**
 * A type which may be converted into a {@link ServerAchievement}.
 * <br>
 * To do so call {@link ServerAchievement#from} with the marked object.
 * <br><br>
 * On applicable Minecraft versions {@code AchievementStat} and {@code Advancement} implement this interface.
 */
public interface IntoServerAchievement {}
