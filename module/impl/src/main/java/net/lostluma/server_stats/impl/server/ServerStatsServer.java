package net.lostluma.server_stats.impl.server;

import net.lostluma.server_stats.entrypoint.common.ModInitializer;
import net.lostluma.server_stats.event.common.GameEvent;
import net.lostluma.server_stats.event.common.ServerWorldEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStatsServer implements ModInitializer {
	@Override
	public void initialize() {
		AtomicBoolean active = new AtomicBoolean();

		ServerWorldEvent.LOAD.register(event -> {
			active.set(true);
			PlayerStatsCache.newInstance(event.path());
		});

		AtomicInteger count = new AtomicInteger();

		GameEvent.TICK.register(event -> {
			if (!active.get()) {
				return;
			}

			// Save player data every 5 minutes
			// Regardless of the real save interval
			// As it is super short on old versions
			if (count.getAndIncrement() == 6000) {
				count.set(0);
				PlayerStatsCache.getInstance().save();
			}
		});

		ServerWorldEvent.STOP.register(event -> {
			active.set(false);
			PlayerStatsCache.closeInstance();
		});
	}
}
