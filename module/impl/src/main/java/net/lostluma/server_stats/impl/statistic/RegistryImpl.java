package net.lostluma.server_stats.impl.statistic;

import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.util.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistryImpl {
	protected static final Map<String, ServerStatisticImpl> BY_KEY = new HashMap<>();
	protected static final Map<Integer, ServerStatisticImpl> BY_VANILLA_ID = new HashMap<>();

	private static final Map<String, String> VANILLA_TO_CUSTOM = getVanillaStatIDs();

	public static void register(ServerStatisticImpl stat) {
		BY_KEY.put(stat.key(), stat);

		if (stat.vanillaId() != -1) {
			BY_VANILLA_ID.put(stat.vanillaId(), stat);
		}
	}

	public static @Nullable ServerStatisticImpl byKey(String key) {
		return BY_KEY.get(key);
	}

	public static @Nullable ServerStatisticImpl byVanillaId(Integer id) {
		return BY_VANILLA_ID.get(id);
	}

	public static void createVanillaStat(int id) {
		// Already registered vanilla stat
		// In static references seen above
		if (!BY_VANILLA_ID.containsKey(id)) {
			new ServerStatisticImpl(id, vanillaName(id));
		}
	}

	public static void createVanillaAchievement(int id, int parentId) {
		if (BY_VANILLA_ID.containsKey(id)) {
			return;
		}

		if (parentId == -1) {
			new ServerAchievementImpl(id, vanillaName(id), null);
		} else {
			ServerStatistic parent = byVanillaId(parentId);
			new ServerAchievementImpl(id, vanillaName(id), (ServerAchievement) parent);
		}
	}

	private static String vanillaName(int id) {
		String key = Integer.toString(id);
		String value = VANILLA_TO_CUSTOM.get(key);

		if (value == null) {
			return "unknown." + id;
		} else {
			// Remove "stat." or "achievement." prefix
			return value.substring(value.indexOf(".") + 1);
		}
	}

	public static void mergeStats(int vanillaId1, int vanillaId2) {
		if (BY_VANILLA_ID.containsKey(vanillaId1) && !BY_VANILLA_ID.containsKey(vanillaId2)) {
			BY_VANILLA_ID.put(vanillaId2, BY_VANILLA_ID.get(vanillaId1));
		} else {
			BY_VANILLA_ID.put(vanillaId1, BY_VANILLA_ID.get(vanillaId2));
		}
	}

	private static Map<String, String> getVanillaStatIDs() {
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		try {
			return Platform.getJsonAsset("statistics.json", type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load vanilla statistics mapping.", e);
		}
	}

	public static Collection<ServerAchievement> achievements() {
		List<ServerAchievement> achievements = new ArrayList<>();

		for (ServerStatisticImpl stat : BY_KEY.values()) {
			if (stat instanceof ServerAchievement) {
				achievements.add((ServerAchievement) stat);
			}
		}

		return achievements;
	}

	public static Collection<ServerStatistic> statistics() {
		List<ServerStatistic> statistics = new ArrayList<>();

		for (ServerStatisticImpl stat : BY_KEY.values()) {
			if (!(stat instanceof ServerAchievement)) {
				statistics.add(stat);
			}
		}

		return statistics;
	}
}
