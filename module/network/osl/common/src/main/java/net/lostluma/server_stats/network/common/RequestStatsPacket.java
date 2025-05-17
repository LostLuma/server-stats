package net.lostluma.server_stats.network.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ornithemc.osl.networking.api.CustomPayload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class RequestStatsPacket implements CustomPayload {
	// One of these must be set when
	// Direction is client -> server
	// Replies will always have both
	private @Nullable String username;
	private @Nullable UUID identifier;

	// May be set on server replies.
	private @Nullable String errorMessage;

	// Always set, but may be empty!
	private @NotNull Map<String, Long> values;

	// OSL constructor

	public RequestStatsPacket() {
		this.values = Collections.emptyMap();
	}

	// Client constructors

	@Environment(EnvType.CLIENT)
	public RequestStatsPacket(@Nullable String username, @Nullable UUID identifier) {
		this.username = username;
		this.identifier = identifier;

		this.values = Collections.emptyMap();
	}

	// Server constructors

	@Environment(EnvType.SERVER)
	public RequestStatsPacket(@Nullable String username, @Nullable UUID identifier, @NotNull Map<String, Long> values) {
		this.username = username;
		this.identifier = identifier;

		this.values = values;
	}

	@Environment(EnvType.SERVER)
	public RequestStatsPacket(@Nullable String username, @Nullable UUID identifier, @NotNull String errorMessage) {
		this.username = username;
		this.identifier = identifier;

		this.errorMessage = errorMessage;
		this.values = Collections.emptyMap();
	}

	public @Nullable String username() {
		return this.username;
	}

	public @Nullable UUID identifier() {
		return this.identifier;
	}

	public @Nullable String errorMessage() {
		return this.errorMessage;
	}

	public @NotNull Map<String, Long> values() {
		return this.values;
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		String username = input.readUTF();

		if (!username.isEmpty()) {
			this.username = username;
		}

		String identifier = input.readUTF();

		if (!identifier.isEmpty()) {
			this.identifier = UUID.fromString(identifier);
		}

		String errorMessage = input.readUTF();

		if (!errorMessage.isEmpty()) {
			this.errorMessage = errorMessage;
		}

		String statistics = input.readUTF();

		Type type = new TypeToken<Map<String, Long>>() {}.getType();
		this.values = new Gson().fromJson(statistics, type);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		if (this.username == null) {
			output.writeUTF("");
		} else {
			output.writeUTF(this.username);
		}

		if (this.identifier == null) {
			output.writeUTF("");
		} else {
			output.writeUTF(this.identifier.toString());
		}

		if (this.errorMessage == null) {
			output.writeUTF("");
		} else {
			output.writeUTF(this.errorMessage);
		}

		output.writeUTF(new Gson().toJson(this.values));
	}
}
