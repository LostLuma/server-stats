package net.lostluma.server_stats.common.stat;

import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.common.util.Platform;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ServerStats {
	protected static Map<String, ServerStat> BY_KEY = new HashMap<>();
	protected static Map<Integer, ServerStat> BY_VANILLA_ID = new HashMap<>();

	private static final Map<String, String> VANILLA_TO_CUSTOM = getVanillaStatIDs();

	public static ServerStat GAMES_LEFT = new ServerStat("stat.leaveGame", 1004).register();
	public static ServerStat DEATHS = new ServerStat("stat.deaths", 2022).register();

	public static ServerStat byKey(String key) {
		return BY_KEY.get(key);
	}

	public static ServerStat byVanillaId(Integer id) {
		return BY_VANILLA_ID.get(id);
	}

	public static ServerStat getEntityKillStat(String entityId) {
		return byKey("stat.killEntity." + entityId);
	}

	public static ServerStat getKilledByEntityStat(String entityId) {
		return byKey("stat.entityKilledBy." + entityId);
	}

	public static void createStat(int vanillaId) {
		// Manually registered statistic
		// That we want a static reference to
		if (BY_VANILLA_ID.containsKey(vanillaId)) {
			return;
		}

		String name = VANILLA_TO_CUSTOM.get(Integer.toString(vanillaId));

		if (name != null) {
			new ServerStat(name, vanillaId).register();
		} else {
			new ServerStat("stat.unknown." + vanillaId, vanillaId).register();
		}
	}

	public static void mergeStats(int vanillaId1, int vanillaId2) {
		if (BY_VANILLA_ID.containsKey(vanillaId1) && !BY_VANILLA_ID.containsKey(vanillaId2)) {
			BY_VANILLA_ID.put(vanillaId2, BY_VANILLA_ID.get(vanillaId1));
		} else {
			BY_VANILLA_ID.put(vanillaId1, BY_VANILLA_ID.get(vanillaId2));
		}
	}

	public static void createEntityKillStat(String entityId) {
		new ServerStat("stat.killEntity." + entityId, null).register();
	}

	public static void createKilledByEntityStat(String entityId) {
		new ServerStat("stat.entityKilledBy." + entityId, null).register();
	}

	private static Map<String, String> getVanillaStatIDs() {
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		try {
			return Platform.getJsonAsset("statistics.json", type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load vanilla statistics mapping.", e);
		}
	}
}
