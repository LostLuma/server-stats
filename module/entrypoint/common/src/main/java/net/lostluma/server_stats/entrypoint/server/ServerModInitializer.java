package net.lostluma.server_stats.entrypoint.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public interface ServerModInitializer {
	void initializeServer();
	String KEY = "server_stats_server_init";
}
