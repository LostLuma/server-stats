package net.lostluma.server_stats.network.common;

import net.lostluma.server_stats.util.Constants;
import net.minecraft.network.packet.CustomPayloadPacket;

import java.nio.charset.StandardCharsets;

public class VersionPacketHelper {
	public static String parse(CustomPayloadPacket packet) {
		return new String(packet.data, StandardCharsets.UTF_8);
	}

	public static CustomPayloadPacket write(String version) {
		CustomPayloadPacket packet = new CustomPayloadPacket();
		byte[] data = version.getBytes(StandardCharsets.UTF_8);

		packet.data = data;
		packet.size = data.length;
		packet.channel = Constants.PROTOCOL_BROADCAST_CHANNEL;

		return packet;
	}
}
