package net.lostluma.server_stats.impl.service;

import net.lostluma.server_stats.impl.ProxyLoader;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface ClientNetworking {
	void fetch(@NotNull String name);
	void fetch(@NotNull UUID identifier);

	ClientNetworking INSTANCE = ProxyLoader.getServiceInstance(ClientNetworking.class);
}
