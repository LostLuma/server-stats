package net.lostluma.server_stats.impl.service;

import net.lostluma.server_stats.impl.ProxyLoader;

public interface ChatBroadcast {
	void announce(String message);
	ChatBroadcast INSTANCE = ProxyLoader.getServiceInstance(ChatBroadcast.class);
}
