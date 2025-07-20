package net.lostluma.server_stats.api.v1.util.convert;

/**
 * A type which may be converted into a {@code ServerAchievement}.
 * <br>
 * To do so call {@code ServerAchievement::from} with the marked object.
 * <br><br>
 * On applicable Minecraft versions {@code AchievementStat} and {@code Advancement} implement this interface.
 */
public interface IntoServerAchievement {}
