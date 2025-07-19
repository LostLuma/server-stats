package net.lostluma.server_stats.impl;

import net.lostluma.server_stats.api.v1.player.MutableStats;
import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.api.v1.util.convert.IntoServerAchievement;
import net.lostluma.server_stats.api.v1.util.convert.IntoServerStatistic;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface ApiProxy {
	static ApiProxy getInstance() {
		return ProxyLoader.INSTANCE;
	}

	// ClientPlayerStats
	void fetch(String name, Consumer<Result<DisplayStats, String>> handler);
	void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler);

	// ServerPlayerStats
	void get(String name, Consumer<Result<MutableStats, String>> handler);
	void get(UUID identifier, Consumer<Result<MutableStats, String>> handler);

	// Registry
	Collection<ServerStatistic> statistics();
	Collection<ServerAchievement> achievements();

	// ServerAchievement
	ServerAchievement convertAchievement(IntoServerAchievement achievement);
	Optional<ServerAchievement> getAchievement(String namespace, String identifier);
	ServerAchievement.Builder buildAchievement(String namespace, String identifier);

	// ServerStatistic
	ServerStatistic convertStatistic(IntoServerStatistic statistic);
	Optional<ServerStatistic> getStatistic(String namespace, String identifier);
	ServerStatistic.Builder buildStatistic(String namespace, String identifier);
}
