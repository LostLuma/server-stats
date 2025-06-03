package net.lostluma.server_stats.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Mojang {
	public static String fetchName(UUID identifier) throws IOException {
		String param = identifier.toString().replace("-", "");
		JsonObject root = fetch("https://api.minecraftservices.com/minecraft/profile/lookup/" + param);

		if (root.has("name")) {
			return root.get("name").getAsString();
		} else {
			throw new IOException("Received incomplete response!");
		}
	}

	public static UUID fetchUuid(String username) throws IOException {
		JsonObject root = fetch("https://api.minecraftservices.com/minecraft/profile/lookup/name/" + username);

		if (!root.has("id")) {
			throw new IOException("Received incomplete response!");
		} else {
			return UUIDUtil.fromMojang(root.get("id").getAsString());
		}
	}

	private static JsonObject fetch(String url) throws IOException {
		URL target = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) target.openConnection();
		connection.setRequestProperty("User-Agent", Constants.USER_AGENT);

		int status = connection.getResponseCode();

		if (status != 200) {
			throw new IOException("Received non-200 status!");
		}

		int size;
		String length = connection.getHeaderField("Content-Length");

		try {
			size = Integer.parseInt(length);
		} catch (NumberFormatException e) {
			throw new IOException("Received invalid Content-Length header!");
		}

		byte[] data = new byte[size];

		try (InputStream stream = connection.getInputStream()) {
			int state;
			int index = 0;

			while ((state = stream.read(data, index, size - index)) > 0) {
				index += state;
			}
		}

		String parsed = new String(data, StandardCharsets.UTF_8);
		@SuppressWarnings("deprecation")
		JsonElement element = new JsonParser().parse(parsed);

		if (element.isJsonObject()) {
			return element.getAsJsonObject();
		} else {
			throw new IOException("Received malformed response!");
		}
	}
}
