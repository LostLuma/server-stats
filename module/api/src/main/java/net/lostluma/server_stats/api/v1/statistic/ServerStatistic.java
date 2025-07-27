package net.lostluma.server_stats.api.v1.statistic;

import net.lostluma.server_stats.api.v1.util.convert.IntoServerStatistic;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;

import java.util.Optional;

/**
 * A Server Stats statistic.
 */
public interface ServerStatistic {
	/**
	 * The namespace of the statistic. Most likely a mod id.
	 *
	 * @return The namespace the statistic was registered with.
	 */
	@Contract(pure = true)
	@NonNls String namespace();

	/**
	 * The statistic's unique identifier within its namespace.
	 *
	 * @return The identifier the statistic was registered with.
	 */
	@Contract(pure = true)
	@NonNls String identifier();

	/**
	 * Look up a statistic from its namespace and path.
	 *
	 * @param namespace The stat's namespace.
	 * @param identifier The stat's identifier.
	 * @return The statistic, if it exists. May be empty.
	 */
	@Contract(pure = true)
	static Optional<ServerStatistic> get(String namespace, String identifier) {
		return ApiProxy.getInstance().getStatistic(namespace, identifier);
	}

	/**
	 * Get the {@code ServerStat} equivalent for a vanilla statistic.
	 *
	 * @param statistic The vanilla statistic.
	 * @return The converted statistic, which can be used with Server Stats APIs.
	 */
	@Contract(pure = true)
	static ServerStatistic from(IntoServerStatistic statistic) {
		return ApiProxy.getInstance().convertStatistic(statistic);
	}

	/**
	 * Create a new statistic builder.
	 *
	 * @param namespace The namespace, most likely your mod id.
	 * @param identifier A unique identifier within the namespace.
	 * @return The statistic builder, used to construct the statistic.
	 */
	@Contract(value = "_, _ -> new", pure = true)
	static Builder of(@NonNls String namespace, @NonNls String identifier) {
		return ApiProxy.getInstance().buildStatistic(namespace, identifier);
	}

	interface Builder {
		/**
		 * Create and register the statistic.
		 *
		 * @return The newly-created statistic.
		 */
		@Contract("-> new")
		ServerStatistic build();
	}
}
