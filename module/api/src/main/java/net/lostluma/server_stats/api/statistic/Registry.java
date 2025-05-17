package net.lostluma.server_stats.api.statistic;

import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Utility for viewing registered statistics.
 * <br>
 * To look up a single statistic or achievement use {@code ServerStatistic.get} or {@code ServerAchievement.get} respectively.
 */
public interface Registry {
	/**
	 * A read-only view of registered statistics.
	 * <br>
	 * Note: For convenience, achievements are not included.
	 *
	 * @return All currently registered statistics.
	 */
	static @NotNull Collection<@NotNull ServerStatistic> statistics() {
		return ApiProxy.getInstance().statistics();
	}

	/**
	 * A read-only view of registered achievements.
	 *
	 * @return All currently registered achievements.
	 */
	static @NotNull Collection<@NotNull ServerAchievement> achievements() {
		return ApiProxy.getInstance().achievements();
	}
}
