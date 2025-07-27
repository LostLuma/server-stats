package net.lostluma.server_stats.network.common;

import net.ornithemc.osl.networking.api.CustomPayload;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Broadcast the Server Stats version to the client.
 */
public class Versionpacket implements CustomPayload {
	private @NotNull String version;

	public Versionpacket() {}

	public Versionpacket(@NotNull String version) {
		this.version = version;
	}

	public String version() {
		return this.version;
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		this.version = input.readUTF();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		output.writeUTF(this.version);
	}
}
