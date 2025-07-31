package net.lostluma.server_stats.broadcast.client.service;

import net.lostluma.server_stats.broadcast.client.Broadcast;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.minecraft.client.C_5664496;
import net.minecraft.client.C_7225666;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		C_5664496 minecraft = Broadcast.client;

		if (minecraft == null) {
			return;
		}

		int max = 320;
		int total = 0;
		String part = "";

		// Add message to GUI in 320-wide parts
		for (int x = 0; x < message.length(); x++) {
			String character = String.valueOf(message.charAt(x));
			int width = minecraft.f_0426313.getStringWidth(character);

			if (total + width <= max) {
				total += width;
				part += character;
			} else {
				minecraft.f_3501374.chatMessages.add(0, new C_7225666(part));

				total = 0;
				part = "";
			}
		}

		if (!part.isEmpty()) {
			minecraft.f_3501374.chatMessages.add(0, new C_7225666(part));
		}

		// Limit chat history to 50 (same as vanilla)
		while(minecraft.f_3501374.chatMessages.size() > 50) {
			minecraft.f_3501374.chatMessages.remove(minecraft.f_3501374.chatMessages.size() - 1);
		}
	}
}
