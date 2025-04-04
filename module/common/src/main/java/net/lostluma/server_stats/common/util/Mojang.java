package net.lostluma.server_stats.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lostluma.server_stats.common.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Mojang {
	public static @NotNull UUID fetchUuid(String username) throws IOException {
		URL url = new URL("https://api.minecraftservices.com/minecraft/profile/lookup/name/" + username);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
		JsonElement element = JsonParser.parseString(parsed);

		if (!element.isJsonObject()) {
			throw new IOException("Received malformed response!");
		}

		JsonObject root = element.getAsJsonObject();

		if (!root.has("id")) {
			throw new IOException("Received incomplete response!");
		} else {
			return UUIDUtil.fromMojang(root.get("id").getAsString());
		}
	}
}
