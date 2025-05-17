package net.lostluma.server_stats.network.common;

import net.ornithemc.osl.networking.api.CustomPayload;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Zero out a single statistic on the client.
 */
public class ZeroStatsPacket implements CustomPayload {
	private String key;

	public ZeroStatsPacket() {}

	public ZeroStatsPacket(String key) {
		this.key = key;
	}

	public String key() {
		return this.key;
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		this.key = input.readUTF();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		output.writeUTF(this.key);
	}
}
