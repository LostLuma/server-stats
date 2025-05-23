package net.lostluma.server_stats.impl;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface ApiProxy {
	static ApiProxy getInstance() {
		return ProxyLoader.INSTANCE;
	}

	// ClientPlayerStats
	void fetch(String name, BiConsumer<@Nullable DisplayStats, @Nullable String> handler);
	void fetch(UUID identifier, BiConsumer<@Nullable DisplayStats, @Nullable String> handler);

	// ServerPlayerStats
	void get(String name, BiConsumer<@Nullable MutableStats, @Nullable String> handler);
	void get(UUID identifier, BiConsumer<@Nullable MutableStats, @Nullable String> handler);

	// Registry
	Collection<ServerStatistic> statistics();
	Collection<ServerAchievement> achievements();

	// ServerAchievement
	ServerAchievement convertAchievement(int id);
	Optional<ServerAchievement> getAchievement(String namespace, String identifier);
	ServerAchievement.Builder buildAchievement(String namespace, String identifier);

	// ServerStatistic
	ServerStatistic convertStatistic(int id);
	Optional<ServerStatistic> getStatistic(String namespace, String identifier);
	ServerStatistic.Builder buildStatistic(String namespace, String identifier);
}
