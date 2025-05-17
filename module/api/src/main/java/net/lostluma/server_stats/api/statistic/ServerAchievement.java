package net.lostluma.server_stats.api.statistic;

import net.lostluma.server_stats.impl.ApiProxy;
import net.minecraft.stat.achievement.AchievementStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Server Stats achievement.
 */
public interface ServerAchievement extends ServerStatistic {
	/**
	 * An optional parent achievement.
	 *
	 * @return The parent achievement, if set.
	 */
	@Nullable ServerAchievement parent();

	/**
	 * Look up an achievement from its namespace and path.
	 *
	 * @param namespace The achievement's namespace.
	 * @param identifier The achievement's identifier.
	 * @return The achievement, if it exists. May be null.
	 */
	static @Nullable ServerAchievement get(@NotNull String namespace, @NotNull String identifier) {
		return ApiProxy.getInstance().getAchievement(namespace, identifier);
	}

	/**
	 * Convert a vanilla achievement to a server achievement.
	 *
	 * @param achievement The vanilla achievement.
	 * @return The converted achievement, which can be used with Server Stats APIs.
	 */
	static @NotNull ServerAchievement from(@NotNull AchievementStat achievement) {
		return ApiProxy.getInstance().convertAchievement(achievement.id);
	}

	/**
	 * Create a new achievement builder.
	 *
	 * @param namespace The namespace, most likely your mod id.
	 * @param identifier A Unique identifier within the namespace.
	 * @return The achievement builder, used to construct the achievement.
	 */
	static @NotNull Builder of(@NotNull String namespace, @NotNull String identifier) {
		return ApiProxy.getInstance().buildAchievement(namespace, identifier);
	}

	interface Builder {
		/**
		 * Create and register the achievement.
		 *
		 * @return The newly-created achievement.
		 */
		@NotNull ServerAchievement build();

		/**
		 * Set a parent achievement.
		 *
		 * @param parent An existing achievement.
		 * @return The achievement builder instance.
		 */
		@NotNull Builder parent(@NotNull ServerAchievement parent);
	}
}
