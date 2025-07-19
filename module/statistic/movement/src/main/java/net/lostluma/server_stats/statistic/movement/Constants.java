package net.lostluma.server_stats.statistic.movement;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import org.jetbrains.annotations.Nullable;

public class Constants {
	private static final Version SPRINTING_ADDED = Version.of("1.0.0-beta.8");

	public static final ServerStatistic CM_CROUCHED = ServerStatistic.of("minecraft", "crouchOneCm").build();
	public static final @Nullable ServerStatistic CM_SPRINTED = registerSprintStat();

	// Exists to load the class without doing anything
	public static void init() {}

	private static @Nullable ServerStatistic registerSprintStat() {
		if (Platform.getModVersion("minecraft").compareTo(Constants.SPRINTING_ADDED) < 0) {
			return null;
		} else {
			return ServerStatistic.of("minecraft", "sprintOneCm").build();
		}
	}
}
