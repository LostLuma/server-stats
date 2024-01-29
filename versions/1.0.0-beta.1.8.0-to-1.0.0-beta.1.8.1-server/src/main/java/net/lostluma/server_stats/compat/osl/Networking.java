package net.lostluma.server_stats.compat.osl;

import net.lostluma.server_stats.Constants;
import net.lostluma.server_stats.stats.ServerPlayerStats;
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
