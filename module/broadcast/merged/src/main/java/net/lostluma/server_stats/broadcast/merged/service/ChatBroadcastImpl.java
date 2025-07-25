package net.lostluma.server_stats.broadcast.merged.service;

import com.google.gson.JsonObject;
import net.lostluma.server_stats.broadcast.merged.Broadcast;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import net.minecraft.network.packet.ChatMessagePacket;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		MinecraftServer server = Broadcast.server;

		if (server == null) {
			return;
		}

		Packet packet;

		if (!this.usesComponents()) {
			packet = new ChatMessagePacket(message);
		} else {
			// The chat message packet still accepts strings, however
			// The client no longer expects this and crashes instead.
			JsonObject root = new JsonObject();
			root.addProperty("text", message);

			message = root.toString();
			packet = new ChatMessagePacket(message);
		}

		server.playerManager.sendPacket(packet);
	}

	/**
	 * Whether this Minecraft version supports / requires usage of text components.
	 */
	private boolean usesComponents() {
		// Snapshot that adds text components
		Version version = Version.of("1.6-alpha.13.21.a");
		return Platform.getModVersion("minecraft").compareTo(version) > -1;
	}
}
