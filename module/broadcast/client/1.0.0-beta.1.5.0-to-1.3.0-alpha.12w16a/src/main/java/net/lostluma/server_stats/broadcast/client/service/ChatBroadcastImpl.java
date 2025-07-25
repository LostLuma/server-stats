package net.lostluma.server_stats.broadcast.client.service;

import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.minecraft.client.Minecraft;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		Minecraft.INSTANCE.gui.addChatMessage(message);
	}
}
