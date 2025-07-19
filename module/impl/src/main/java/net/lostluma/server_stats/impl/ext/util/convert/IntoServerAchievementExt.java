package net.lostluma.server_stats.impl.ext.util.convert;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.util.convert.IntoServerAchievement;

public interface IntoServerAchievementExt extends IntoServerAchievement {
	default ServerAchievement server_stats$into() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
