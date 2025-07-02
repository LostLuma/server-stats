package net.lostluma.server_stats.statistic.movement;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.util.platform.Version;

public class Constants {
	public static Version SPRINTING_ADDED = Version.of("1.0.0-beta.8");

	public static ServerStatistic CM_CROUCHED = ServerStatistic.of("minecraft", "stat.crouchOneCm").build();
	public static ServerStatistic CM_SPRINTED = ServerStatistic.of("minecraft", "stat.sprintOneCm").build();

	// Exists to load the class without doing anything
	public static void load() {
	}
}
