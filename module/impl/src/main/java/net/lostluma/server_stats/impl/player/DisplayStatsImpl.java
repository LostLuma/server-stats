package net.lostluma.server_stats.impl.player;

import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;

import java.util.Map;

public class DisplayStatsImpl implements DisplayStats {
	private final Map<String, Long> values;

	public DisplayStatsImpl(Map<String, Long> values) {
		this.values = values;
	}

	@Override
	public long get(ServerStatistic stat) {
		return this.values.getOrDefault(this.getKey(stat), 0L);
	}

	private String getKey(ServerStatistic stat) {
		return ((ServerStatisticImpl) stat).key();
	}
}
