package net.lostluma.server_stats;

import com.google.common.collect.Maps;

import java.util.Map;

public class UUIDHelper {
	private static final Map<String, String> uuids = Maps.newConcurrentMap();

	public static String getUuid(String name) {
		return uuids.get(name);
	}

	public static void setUuid(String name, String uuid) {
		uuids.put(name, uuid);
	}
}
