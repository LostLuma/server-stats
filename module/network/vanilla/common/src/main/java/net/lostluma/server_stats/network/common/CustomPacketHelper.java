package net.lostluma.server_stats.network.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.minecraft.network.packet.CustomPayloadPacket;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomPacketHelper {
	public static Map<String, Long> parse(CustomPayloadPacket packet) {
		String data = new String(packet.data, StandardCharsets.UTF_8);
		Type type = new TypeToken<Map<String, Long>>() {}.getType();

		return new Gson().fromJson(data, type);
	}

	public static CustomPayloadPacket write(ServerPlayerStats stats, boolean large) {
		CustomPayloadPacket packet = new CustomPayloadPacket();
		byte[] data = stats.serialize(large).getBytes(StandardCharsets.UTF_8);

		packet.data = data;
		packet.size = data.length;

		if (!large) {
			packet.channel = Constants.STATS_PACKET_SMALL_CHANNEL;
		} else {
			packet.channel = Constants.STATS_PACKET_LARGE_CHANNEL;
		}

		return packet;
	}
}
