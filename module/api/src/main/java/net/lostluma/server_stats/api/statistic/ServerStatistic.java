package net.lostluma.server_stats.api.statistic;

import net.lostluma.server_stats.impl.ApiProxy;
import net.minecraft.stat.Stat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Server Stats statistic.
 */
public interface ServerStatistic {
	/**
	 * The namespace of the statistic. Most likely a mod id.
	 *
	 * @return The namespace the statistic was registered with.
	 */
	@NotNull String namespace();

	/**
	 * The statistic's unique identifier within its namespace.
	 *
	 * @return The identifier the statistic was registered with.
	 */
	@NotNull String identifier();

	/**
	 * Look up a statistic from its namespace and path.
	 *
	 * @param namespace The stat's namespace.
	 * @param identifier The stat's identifier.
	 * @return The statistic, if it exists. May be null.
	 */
	static @Nullable ServerStatistic get(@NotNull String namespace, @NotNull String identifier) {
		return ApiProxy.getInstance().getStatistic(namespace, identifier);
	}

	/**
	 * Get the {@code ServerStat} equivalent for a vanilla statistic.
	 *
	 * @param stat The vanilla statistic.
	 * @return The converted statistic, which can be used with Server Stats APIs.
	 */
	static @NotNull ServerStatistic from(@NotNull Stat stat) {
		return ApiProxy.getInstance().convertStatistic(stat.id);
	}

	/**
	 * Create a new statistic builder.
	 *
	 * @param namespace The namespace, most likely your mod id.
	 * @param identifier A Unique identifier within the namespace.
	 * @return The statistic builder, used to construct the statistic.
	 */
	static @NotNull Builder of(@NotNull String namespace, @NotNull String identifier) {
		return ApiProxy.getInstance().buildStatistic(namespace, identifier);
	}

	interface Builder {
		/**
		 * Create and register the statistic.
		 *
		 * @return The newly-created statistic.
		 */
		@NotNull ServerStatistic build();
	}
}
