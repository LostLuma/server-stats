package net.lostluma.server_stats.impl.ext.util.convert;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.api.util.convert.IntoServerStatistic;

public interface IntoServerStatisticExt extends IntoServerStatistic {
	default ServerStatistic server_stats$into() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
