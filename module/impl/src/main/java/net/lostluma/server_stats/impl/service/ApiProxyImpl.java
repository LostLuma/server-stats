package net.lostluma.server_stats.impl.service;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.api.util.Result;
import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.server.ServerPlayerStatsImpl;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.lostluma.server_stats.impl.statistic.ServerAchievementImpl;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.lostluma.server_stats.impl.ApiProxy;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class ApiProxyImpl implements ApiProxy {
	// ClientPlayerStats

	@Override
	public void fetch(String name, Consumer<Result<DisplayStats, String>> handler) {
		ClientPlayerStatsImpl.fetch(name, handler);
	}

	@Override
	public void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		ClientPlayerStatsImpl.fetch(identifier, handler);
	}

	// ServerPlayerStats

	@Override
	public void get(String name, Consumer<Result<MutableStats, String>> handler) {
		ServerPlayerStatsImpl.get(name, handler);
	}

	@Override
	public void get(UUID identifier, Consumer<Result<MutableStats, String>> handler) {
		ServerPlayerStatsImpl.get(identifier, handler);
	}

	// Registry

	@Override
	public Collection<ServerStatistic> statistics() {
		return RegistryImpl.statistics();
	}

	@Override
	public Collection<ServerAchievement> achievements() {
		return RegistryImpl.achievements();
	}

	// ServerAchievement

	@Override
	public ServerAchievement convertAchievement(int id) {
		return (ServerAchievement) this.convertStatistic(id);
	}

	@Override
	public Optional<ServerAchievement> getAchievement(String namespace, String identifier) {
		return Optional.ofNullable((ServerAchievement) this.byKey("achievement", namespace, identifier));
	}

	@Override
	public ServerAchievement.Builder buildAchievement(String namespace, String identifier) {
		return new ServerAchievementImpl.Builder(namespace, identifier);
	}

	// ServerStatistic

	@Override
	public ServerStatistic convertStatistic(int id) {
		ServerStatistic statistic = RegistryImpl.byVanillaId(id);

		if (statistic != null) {
			return statistic;
		} else {
			throw new NullPointerException("No statistic with id " + id + " is registered.");
		}
	}

	@Override
	public Optional<ServerStatistic> getStatistic(String namespace, String identifier) {
		return Optional.ofNullable(this.byKey("stat", namespace, identifier));
	}

	@Override
	public ServerStatistic.Builder buildStatistic(String namespace, String identifier) {
		return new ServerStatisticImpl.Builder(namespace, identifier);
	}

	private @Nullable ServerStatistic byKey(String prefix, String namespace, String identifier) {
		if (namespace.equals("minecraft")) {
			return RegistryImpl.byKey(prefix + "." + identifier);
		} else {
			return RegistryImpl.byKey(prefix + "." + namespace + "." + identifier);
		}
	}
}
