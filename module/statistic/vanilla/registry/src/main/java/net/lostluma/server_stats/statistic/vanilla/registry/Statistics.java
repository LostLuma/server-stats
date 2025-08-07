package net.lostluma.server_stats.statistic.vanilla.registry;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Statistics {
	public static final ServerStatistic GAMES_LEFT = ServerStatistic.of("minecraft", "leaveGame").build();
	public static final ServerStatistic MINUTES_PLAYED = ServerStatistic.of("minecraft", "playOneMinute").build();
	public static final ServerStatistic CM_WALKED = ServerStatistic.of("minecraft", "walkOneCm").build();
	public static final ServerStatistic CM_SWUM = ServerStatistic.of("minecraft", "swimOneCm").build();
	public static final ServerStatistic CM_FALLEN = ServerStatistic.of("minecraft", "fallOneCm").build();
	public static final ServerStatistic CM_CLIMB = ServerStatistic.of("minecraft", "climbOneCm").build();
	public static final ServerStatistic CM_FLOWN = ServerStatistic.of("minecraft", "flyOneCm").build();
	public static final ServerStatistic CM_DOVE = ServerStatistic.of("minecraft", "diveOneCm").build();
	public static final ServerStatistic CM_MINECART = registerConditionally("minecartOneCm", "0.31.20100624");
	public static final ServerStatistic CM_SAILED = registerConditionally("boatOneCm", "1.0.0-alpha.0.6");
	public static final ServerStatistic CM_PIG = registerConditionally("pigOneCm", "0.31.20100625-1917");
	public static final ServerStatistic JUMPS = ServerStatistic.of("minecraft", "jump").build();
	public static final ServerStatistic DROPS = ServerStatistic.of("minecraft", "drop").build();
	public static final ServerStatistic DAMAGE_DEALT = ServerStatistic.of("minecraft", "damageDealt").build();
	public static final ServerStatistic DAMAGE_TAKEN = ServerStatistic.of("minecraft", "damageTaken").build();
	public static final ServerStatistic DEATHS = ServerStatistic.of("minecraft", "deaths").build();
	public static final ServerStatistic MOBS_KILLED = ServerStatistic.of("minecraft", "mobKills").build();
	public static final ServerStatistic PLAYERS_KILLED = ServerStatistic.of("minecraft", "playerKills").build();
	public static final ServerStatistic CATCH_FISH = registerConditionally("fishCaught", "1.0.0-alpha.1.1");

	public static void init() {}

	public static ServerStatistic mineBlock(int id) {
		Optional<ServerStatistic> result = ServerStatistic.get("minecraft", "mineBlock." + id);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Getting statistic for unregistered block " + id);
		}
	}

	public static ServerStatistic breakItem(int id) {
		Optional<ServerStatistic> result = ServerStatistic.get("minecraft", "breakItem." + id);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Getting statistic for unregistered item " + id);
		}
	}

	public static ServerStatistic craftItem(int id) {
		Optional<ServerStatistic> result = ServerStatistic.get("minecraft", "craftItem." + id);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Getting statistic for unregistered item " + id);
		}
	}

	public static ServerStatistic useItem(int id) {
		Optional<ServerStatistic> result = ServerStatistic.get("minecraft", "useItem." + id);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Getting statistic for unregistered item " + id);
		}
	}

	private static @Nullable ServerStatistic registerConditionally(String identifier, String startVersion) {
		Version want = Version.of(startVersion);
		Version game = Platform.getModVersion("minecraft");

		if (want.compareTo(game) >= 0) {
			return null;
		} else {
			return ServerStatistic.of("minecraft", identifier).build();
		}
	}
}
