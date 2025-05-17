package net.lostluma.server_stats.network.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.util.Logging;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class RequestPacketHelper {
	public static @Nullable RequestPacketData parse(CustomPayloadPacket packet) {
		ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
		DataInputStream reader = new DataInputStream(stream);

		String username;
		String rawIdentifier;
		String errorMessage;
		String statistics;

		try {
			username = reader.readUTF();
			rawIdentifier = reader.readUTF();
			errorMessage = reader.readUTF();
			statistics = reader.readUTF();
		} catch (IOException e) {
			Logging.getLogger().warn("Received invalid stats request packet.", e);
			return null;
		}

		UUID identifier = null;

		if (!rawIdentifier.isEmpty()) {
			identifier = UUID.fromString(rawIdentifier);
		}

		Type type = new TypeToken<Map<String, Long>>() {}.getType();
		Map<String, Long> values = new Gson().fromJson(statistics, type);

		return new RequestPacketData(username, identifier, errorMessage, values);
	}

	public static CustomPayloadPacket write(@Nullable String username, @Nullable UUID identifier) {
		return write(username, identifier, null, Collections.emptyMap());
	}

	public static CustomPayloadPacket write(@Nullable String username, @Nullable UUID identifier, @Nullable String errorMessage, @NotNull Map<String, Long> values) {
		CustomPayloadPacket packet = new CustomPayloadPacket();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(stream);

		try {
			if (username == null) {
				writer.writeUTF("");
			} else {
				writer.writeUTF(username);
			}

			if (identifier == null) {
				writer.writeUTF("");
			} else {
				writer.writeUTF(identifier.toString());
			}

			if (errorMessage == null) {
				writer.writeUTF("");
			} else {
				writer.writeUTF(errorMessage);
			}

			writer.writeUTF(new Gson().toJson(values));
		} catch (IOException e) {
			throw new RuntimeException("Failed to write stats request packet.", e);
		}

		byte[] data = stream.toByteArray();

		packet.data = data;
		packet.size = data.length;

		packet.channel = Constants.STATS_PACKET_FETCH_CHANNEL;

		return packet;
	}

	public static final class RequestPacketData {
		public final @Nullable String username;
		public final @Nullable UUID identifier;

		public final @Nullable String errorMessage;
		public final @NotNull Map<String, Long> values;

		private RequestPacketData(@Nullable String username, @Nullable UUID identifier, @Nullable String errorMessage, @NotNull Map<String, Long> values) {
			this.username = username;
			this.identifier = identifier;

			this.errorMessage = errorMessage;
			this.values = values;
		}
	}
}
