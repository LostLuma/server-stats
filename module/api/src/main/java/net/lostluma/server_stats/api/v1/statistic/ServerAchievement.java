package net.lostluma.server_stats.api.v1.statistic;

import net.lostluma.server_stats.api.v1.util.convert.IntoServerAchievement;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;

import java.util.Optional;

/**
 * A Server Stats achievement.
 */
public interface ServerAchievement extends ServerStatistic {
	/**
	 * An optional parent achievement.
	 *
	 * @return The parent achievement, if set.
	 */
	@Contract(pure = true)
	Optional<ServerAchievement> parent();

	/**
	 * Look up an achievement from its namespace and path.
	 *
	 * @param namespace The achievement's namespace.
	 * @param identifier The achievement's identifier.
	 * @return The achievement, if it exists. May be empty.
	 */
	@Contract(pure = true)
	static Optional<ServerAchievement> get(String namespace, String identifier) {
		return ApiProxy.getInstance().getAchievement(namespace, identifier);
	}

	/**
	 * Convert a vanilla achievement to a server achievement.
	 *
	 * @param achievement The vanilla achievement.
	 * @return The converted achievement, which can be used with Server Stats APIs.
	 */
	@Contract(pure = true)
	static ServerAchievement from(IntoServerAchievement achievement) {
		return ApiProxy.getInstance().convertAchievement(achievement);
	}

	/**
	 * Create a new achievement builder.
	 *
	 * @param namespace The namespace, most likely your mod id.
	 * @param identifier A unique identifier within the namespace.
	 * @return The achievement builder, used to construct the achievement.
	 */
	@Contract(value = "_, _ -> new", pure = true)
	static Builder of(@NonNls String namespace, @NonNls String identifier) {
		return ApiProxy.getInstance().buildAchievement(namespace, identifier);
	}

	interface Builder {
		/**
		 * Create and register the achievement.
		 *
		 * @return The newly-created achievement.
		 */
		@Contract("-> new")
		ServerAchievement build();

		/**
		 * Set a parent achievement.
		 *
		 * @param parent An existing achievement.
		 * @return The achievement builder instance.
		 */
		@Contract("_ -> this")
		Builder parent(ServerAchievement parent);
	}
}
