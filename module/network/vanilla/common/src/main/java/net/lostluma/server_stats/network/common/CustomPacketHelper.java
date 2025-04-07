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
	public static Map<String, Integer> parse(CustomPayloadPacket packet) {
		String data = new String(packet.data, StandardCharsets.UTF_8);
		Type type = new TypeToken<Map<String, Integer>>() {}.getType();

		return new Gson().fromJson(data, type);
	}

	public static CustomPayloadPacket write(ServerPlayerStats stats) {
		CustomPayloadPacket packet = new CustomPayloadPacket();
		byte[] data = stats.serialize().getBytes(StandardCharsets.UTF_8);

		packet.data = data;
		packet.size = data.length;

		packet.channel = Constants.STATS_PACKET_CHANNEL;

		return packet;
	}
}
