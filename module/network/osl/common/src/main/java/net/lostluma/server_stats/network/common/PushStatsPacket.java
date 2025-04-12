package net.lostluma.server_stats.network.common;

import net.ornithemc.osl.networking.api.CustomPayload;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PushStatsPacket implements CustomPayload {
	private String key;
	private long value;

	public PushStatsPacket() {}

	public PushStatsPacket(String key, long value) {
		this.key = key;
		this.value = value;
	}

	public String key() {
		return this.key;
	}

	public long value() {
		return this.value;
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		this.key = input.readUTF();
		this.value = input.readLong();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		output.writeUTF(this.key);
		output.writeLong(this.value);
	}
}
