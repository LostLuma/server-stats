package net.lostluma.server_stats.compat.osl;

import net.lostluma.server_stats.Constants;
import net.minecraft.client.Minecraft;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;

public class Networking implements ClientModInitializer {
    @Override
    public void initClient() {
        ClientPlayNetworking.registerListener(Constants.STATS_PACKET_CHANNEL, SyncStatsPacket::new, ((minecraft, handler, payload) -> {
            Minecraft.INSTANCE.statHandler.player_stats$override(payload.data());
            return true;
        }));
    }
}
