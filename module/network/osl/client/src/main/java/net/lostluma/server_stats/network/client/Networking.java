package net.lostluma.server_stats.network.client;

import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.network.common.RequestStatsPacket;
import net.lostluma.server_stats.network.common.ZeroStatsPacket;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.network.common.SyncStatsPacket;
import net.lostluma.server_stats.network.common.PushStatsPacket;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;

public class Networking implements ClientModInitializer {
	@Override
	public void initClient() {
		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_SMALL_CHANNEL, SyncStatsPacket::new, ((minecraft, handler, payload) -> {
			minecraft.statHandler.server_stats$persist(payload.data(), true);
			return true;
		}));

		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_LARGE_CHANNEL, SyncStatsPacket::new, ((minecraft, handler, payload) -> {
			minecraft.statHandler.server_stats$persist(payload.data(), false);
			return true;
		}));

		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_AMEND_CHANNEL, PushStatsPacket::new, (((minecraft, handler, payload) -> {
			minecraft.statHandler.server_stats$add(payload.key(), payload.value());
			return true;
		})));

		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_RESET_CHANNEL, ZeroStatsPacket::new, (((minecraft, handler, payload) -> {
			minecraft.statHandler.server_stats$reset(payload.key());
			return true;
		})));

		ClientPlayNetworking.registerListener(Constants.STATS_PACKET_FETCH_CHANNEL, RequestStatsPacket::new, (((minecraft, handler, payload) -> {
			ClientPlayerStatsImpl.onResponse(payload.username(), payload.identifier(), payload.values(), payload.errorMessage());
			return true;
		})));
	}
}
