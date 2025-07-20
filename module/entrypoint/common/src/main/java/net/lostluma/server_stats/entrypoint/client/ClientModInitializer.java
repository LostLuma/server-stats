package net.lostluma.server_stats.entrypoint.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ClientModInitializer {
	void initializeClient();
	String KEY = "server_stats_client_init";
}
