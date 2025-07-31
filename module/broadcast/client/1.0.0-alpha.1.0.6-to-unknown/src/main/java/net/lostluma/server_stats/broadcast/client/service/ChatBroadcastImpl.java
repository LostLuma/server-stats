package net.lostluma.server_stats.broadcast.client.service;

import net.lostluma.server_stats.broadcast.client.Broadcast;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatMessage;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		Minecraft minecraft = Broadcast.client;

		if (minecraft == null) {
			return;
		}

		int max = 320;
		int total = 0;
		String part = "";

		// Add message to GUI in 320-wide parts
		for (int x = 0; x < message.length(); x++) {
			String character = String.valueOf(message.charAt(x));
			int width = minecraft.textRenderer.getStringWidth(character);

			if (total + width <= max) {
				total += width;
				part += character;
			} else {
				minecraft.gui.chatMessages.add(0, new ChatMessage(part));

				total = 0;
				part = "";
			}
		}

		if (!part.isEmpty()) {
			minecraft.gui.chatMessages.add(0, new ChatMessage(part));
		}

		// Limit chat history to 50 (same as vanilla)
		while(minecraft.gui.chatMessages.size() > 50) {
			minecraft.gui.chatMessages.remove(minecraft.gui.chatMessages.size() - 1);
		}
	}
}
