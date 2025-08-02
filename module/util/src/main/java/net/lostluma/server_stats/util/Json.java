package net.lostluma.server_stats.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class Json {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static String serialize(JsonElement element) {
		return GSON.toJson(element);
	}
}
