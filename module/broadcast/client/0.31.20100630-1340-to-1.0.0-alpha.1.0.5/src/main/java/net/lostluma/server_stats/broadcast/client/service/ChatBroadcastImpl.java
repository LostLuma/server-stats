package net.lostluma.server_stats.broadcast.client.service;

import net.lostluma.server_stats.broadcast.client.Broadcast;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.minecraft.client.gui.ChatMessage;
import net.minecraft.unmapped.C_5664496;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	@SuppressWarnings("unchecked")
	public void announce(String message) {
		C_5664496 minecraft = Broadcast.client;

		if (minecraft == null) {
			return;
		}

		int max = 320;
		int total = 0;
		StringBuilder piece = new StringBuilder();

		// Add message to GUI in 320-wide parts
		for (int x = 0; x < message.length(); x++) {
			String character = String.valueOf(message.charAt(x));
			int width = minecraft.f_0426313.getStringWidth(character);

			if (total + width <= max) {
				total += width;
				piece.append(character);
			} else {
				minecraft.f_3501374.chatMessages.add(0, new ChatMessage(piece.toString()));

				total = 0;
				piece = new StringBuilder();
			}
		}

		if (piece.length() > 0) {
			minecraft.f_3501374.chatMessages.add(0, new ChatMessage(piece.toString()));
		}

		// Limit chat history to 50 (same as vanilla)
		while(minecraft.f_3501374.chatMessages.size() > 50) {
			minecraft.f_3501374.chatMessages.remove(minecraft.f_3501374.chatMessages.size() - 1);
		}
	}
}
