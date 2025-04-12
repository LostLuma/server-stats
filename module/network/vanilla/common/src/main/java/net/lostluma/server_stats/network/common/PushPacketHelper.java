package net.lostluma.server_stats.network.common;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.util.Tuple;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PushPacketHelper {
	public static Tuple<String, Long> parse(CustomPayloadPacket packet) {
		ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
		DataInputStream reader = new DataInputStream(stream);

		String key;
		long value;

		try {
			key = reader.readUTF();
			value = reader.readLong();
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse stats push packet.", e);
		}

		return new Tuple<>(key, value);
	}

	public static CustomPayloadPacket write(@NotNull ServerStat stat, long value) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(stream);

		try {
			writer.writeUTF(stat.key);
			writer.writeLong(value);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write stats push packet.", e);
		}

		byte[] data = stream.toByteArray();
		CustomPayloadPacket packet = new CustomPayloadPacket();

		packet.data = data;
		packet.size = data.length;
		packet.channel = Constants.STATS_PACKET_AMEND_CHANNEL;

		return packet;
	}
}
