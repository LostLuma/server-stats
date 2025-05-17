package net.lostluma.server_stats.impl.service;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.server.ServerPlayerStatsImpl;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.lostluma.server_stats.impl.statistic.ServerAchievementImpl;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ApiProxyImpl implements ApiProxy {
	// ClientPlayerStats

	@Override
	public void fetch(@NotNull String name, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler) {
		ClientPlayerStatsImpl.fetch(name, handler);
	}

	@Override
	public void fetch(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler) {
		ClientPlayerStatsImpl.fetch(identifier, handler);
	}

	// ServerPlayerStats

	@Override
	public void get(@NotNull String name, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		ServerPlayerStatsImpl.get(name, handler);
	}

	@Override
	public void get(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		ServerPlayerStatsImpl.get(identifier, handler);
	}

	// Registry

	@Override
	public @NotNull Collection<@NotNull ServerStatistic> statistics() {
		return RegistryImpl.statistics();
	}

	@Override
	public @NotNull Collection<@NotNull ServerAchievement> achievements() {
		return RegistryImpl.achievements();
	}

	// ServerAchievement

	@Override
	public @NotNull ServerAchievement convertAchievement(int id) {
		return (ServerAchievement) RegistryImpl.byVanillaId(id);
	}

	@Override
	public @Nullable ServerAchievement getAchievement(@NotNull String namespace, @NotNull String identifier) {
		ServerStatistic stat = this.byKey("achievement", namespace, identifier);

		if (stat == null) {
			return null;
		} else {
			return (ServerAchievement) stat;
		}
	}

	@Override
	public @NotNull ServerAchievement.Builder buildAchievement(@NotNull String namespace, @NotNull String identifier) {
		return new ServerAchievementImpl.Builder(namespace, identifier);
	}

	// ServerStatistic

	@Override
	public @NotNull ServerStatistic convertStatistic(int id) {
		return RegistryImpl.byVanillaId(id);
	}

	@Override
	public @Nullable ServerStatistic getStatistic(@NotNull String namespace, @NotNull String identifier) {
		return this.byKey("stat", namespace, identifier);
	}

	@Override
	public @NotNull ServerStatistic.Builder buildStatistic(@NotNull String namespace, @NotNull String identifier) {
		return new ServerStatisticImpl.Builder(namespace, identifier);
	}

	private @Nullable ServerStatistic byKey(@NotNull String prefix, @NotNull String namespace, @NotNull String identifier) {
		if (namespace.equals("minecraft")) {
			return RegistryImpl.byKey(prefix + "." + identifier);
		} else {
			return RegistryImpl.byKey(prefix + "." + namespace + "." + identifier);
		}
	}
}
