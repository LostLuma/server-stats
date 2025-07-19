package net.lostluma.server_stats.network.common;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.lostluma.server_stats.util.Constants;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ZeroPacketHelper {
	public static String parse(CustomPayloadPacket packet) {
		ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
		DataInputStream reader = new DataInputStream(stream);

		String key;

		try {
			key = reader.readUTF();
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse stats zero packet.", e);
		}

		return key;
	}

	public static CustomPayloadPacket write(@NotNull ServerStatistic stat) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(stream);

		try {
			writer.writeUTF(((ServerStatisticImpl) stat).key());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write stats zero packet.", e);
		}

		byte[] data = stream.toByteArray();
		CustomPayloadPacket packet = new CustomPayloadPacket();

		packet.data = data;
		packet.size = data.length;
		packet.channel = Constants.STATS_PACKET_RESET_CHANNEL;

		return packet;
	}
}
