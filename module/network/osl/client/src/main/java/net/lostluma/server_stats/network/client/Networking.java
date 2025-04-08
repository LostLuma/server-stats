package net.lostluma.server_stats.network.client;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.network.common.SyncStatsPacket;
import net.minecraft.client.Minecraft;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;

public class Networking implements ClientModInitializer {
	@Override
	public void initClient() {
		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_SMALL_CHANNEL, SyncStatsPacket::new, ((minecraft, handler, payload) -> {
			Minecraft.INSTANCE.statHandler.server_stats$persist(payload.data(), true);
			return true;
		}));

		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_LARGE_CHANNEL, SyncStatsPacket::new, ((minecraft, handler, payload) -> {
			Minecraft.INSTANCE.statHandler.server_stats$persist(payload.data(), false);
			return true;
		}));
	}
}
