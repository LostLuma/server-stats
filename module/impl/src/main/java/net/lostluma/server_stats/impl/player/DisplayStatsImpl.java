package net.lostluma.server_stats.impl.player;

import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DisplayStatsImpl implements DisplayStats {
	private final @NotNull Map<String, Long> values;

	public DisplayStatsImpl(@NotNull Map<String, Long> values) {
		this.values = values;
	}

	@Override
	public long get(@NotNull ServerStatistic stat) {
		return this.values.getOrDefault(this.getKey(stat), 0L);
	}

	private @NotNull String getKey(@NotNull ServerStatistic stat) {
		return ((ServerStatisticImpl) stat).key();
	}
}
