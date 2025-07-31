package net.lostluma.server_stats.broadcast.client.service;

import net.lostluma.server_stats.impl.service.ChatBroadcast;

public class ChatBroadcastImpl implements ChatBroadcast {
	@Override
	public void announce(String message) {
		// I'm not sure how adding chat messages to the GUI works here
		// So for now (or maybe forever) this feature will not work on these versions
	}
}
