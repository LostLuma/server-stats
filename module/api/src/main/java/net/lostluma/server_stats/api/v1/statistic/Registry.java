package net.lostluma.server_stats.api.v1.statistic;

import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;

/**
 * Utility for viewing registered statistics.
 * <br>
 * To look up a single statistic or achievement use {@link ServerStatistic#get} or {@link ServerAchievement#get} respectively.
 */
public interface Registry {
	/**
	 * A read-only view of registered statistics.
	 * <br>
	 * Note: For convenience, achievements are not included.
	 *
	 * @return All currently registered statistics.
	 */
	@Contract(pure = true)
	static @Unmodifiable Collection<ServerStatistic> statistics() {
		return ApiProxy.getInstance().statistics();
	}

	/**
	 * A read-only view of registered achievements.
	 *
	 * @return All currently registered achievements.
	 */
	@Contract(pure = true)
	static @Unmodifiable Collection<ServerAchievement> achievements() {
		return ApiProxy.getInstance().achievements();
	}
}
