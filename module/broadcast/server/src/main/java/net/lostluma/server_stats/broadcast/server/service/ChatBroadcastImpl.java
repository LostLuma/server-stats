package net.lostluma.server_stats.broadcast.server.service;

import net.lostluma.server_stats.broadcast.server.Broadcast;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.minecraft.network.packet.ChatMessagePacket;
import net.minecraft.server.MinecraftServer;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		MinecraftServer server = Broadcast.server;

		if (server != null) {
			server.playerManager.sendPacket(new ChatMessagePacket(message));
		}
	}
}
