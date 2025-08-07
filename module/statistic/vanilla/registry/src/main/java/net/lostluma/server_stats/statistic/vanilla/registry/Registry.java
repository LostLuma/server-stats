package net.lostluma.server_stats.statistic.vanilla.registry;

import net.lostluma.server_stats.entrypoint.common.ModInitializer;

public class Registry implements ModInitializer {
	@Override
	public void initialize() {
		Statistics.init();
		Achievements.init();
	}
}
