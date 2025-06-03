package net.lostluma.server_stats.impl.service;

import net.lostluma.server_stats.impl.ProxyLoader;

import java.util.UUID;

public interface ClientNetworking {
	void fetch(String name);
	void fetch(UUID identifier);

	ClientNetworking INSTANCE = ProxyLoader.getServiceInstance(ClientNetworking.class);
}
