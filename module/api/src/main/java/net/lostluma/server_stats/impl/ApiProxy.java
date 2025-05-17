package net.lostluma.server_stats.impl;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;

@ApiStatus.Internal
public interface ApiProxy {
	static ApiProxy getInstance() {
		return ProxyLoader.INSTANCE;
	}

	// ClientPlayerStats
	void fetch(@NotNull String name, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler);
	void fetch(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler);

	// ServerPlayerStats
	void get(@NotNull String name, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler);
	void get(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler);

	// Registry
	@NotNull Collection<@NotNull ServerStatistic> statistics();
	@NotNull Collection<@NotNull ServerAchievement> achievements();

	// ServerAchievement
	@NotNull ServerAchievement convertAchievement(int id);
	@Nullable ServerAchievement getAchievement(@NotNull String namespace, @NotNull String identifier);
	@NotNull ServerAchievement.Builder buildAchievement(@NotNull String namespace, @NotNull String identifier);

	// ServerStatistic
	@NotNull ServerStatistic convertStatistic(int id);
	@Nullable ServerStatistic getStatistic(@NotNull String namespace, @NotNull String identifier);
	@NotNull ServerStatistic.Builder buildStatistic(@NotNull String namespace, @NotNull String identifier);
}
