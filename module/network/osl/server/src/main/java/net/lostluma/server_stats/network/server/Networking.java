package net.lostluma.server_stats.network.server;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.network.common.SyncStatsPacket;
import net.ornithemc.osl.entrypoints.api.server.ServerModInitializer;
import net.ornithemc.osl.networking.api.server.ServerConnectionEvents;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;

public class Networking implements ServerModInitializer {
	@Override
	public void initServer() {
		ServerConnectionEvents.PLAY_READY.register((server, player) -> {
			ServerPlayerStats stats = player.server_stats$getStats();

			if (stats != null) {
				ServerPlayNetworking.send(player, Constants.STATS_PACKET_CHANNEL, new SyncStatsPacket(stats));
			}
		});
	}
}
