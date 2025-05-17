package net.lostluma.server_stats.impl.server;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.player.PersistentStatsImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Proxy to an underlying PersistentStats instance.
 * <br>
 * Allows the player stats cache to clean up the underlying instance once the proxy is garbage collected.
 */
public class PersistentStatsProxy implements PersistentStats {
	private final PersistentStatsImpl parent;

	public PersistentStatsProxy(PersistentStatsImpl parent) {
		this.parent = parent;
	}

	@Override
	public long get(@NotNull ServerStatistic stat) {
		return this.parent.get(stat);
	}

	@Override
	public long reset(@NotNull ServerStatistic stat) throws IllegalStateException {
		return this.parent.reset(stat);
	}

	@Override
	public long increment(@NotNull ServerStatistic stat, long amount) throws IllegalStateException {
		return this.parent.increment(stat, amount);
	}

	@Override
	public void server_stats$save() {
		this.parent.server_stats$save();
	}

	@Override
	public void server_stats$close() {
		this.parent.server_stats$close();
	}

	@Override
	public Map<String, Long> server_stats$values() {
		return this.parent.server_stats$values();
	}

	@Override
	public @NotNull String server_stats$serialize(boolean large) {
		return this.parent.server_stats$serialize(large);
	}
}
