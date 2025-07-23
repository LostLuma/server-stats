package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.entrypoint.client.ClientModInitializer;
import net.lostluma.server_stats.event.client.ClientWorldEvent;

public class ServerStatsClient implements ClientModInitializer {
	@Override
	public void initializeClient() {
		ClientWorldEvent.JOIN.register(event -> {
			ClientPlayerStatsImpl.setInWorld(true);
		});

		ClientWorldEvent.LEFT.register(event -> {
			ClientPlayerStatsImpl.setInWorld(false);
		});
	}
}
