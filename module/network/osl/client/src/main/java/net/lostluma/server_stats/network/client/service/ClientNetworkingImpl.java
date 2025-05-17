package net.lostluma.server_stats.network.client.service;

import net.lostluma.server_stats.impl.service.ClientNetworking;
import net.lostluma.server_stats.network.common.RequestStatsPacket;
import net.lostluma.server_stats.util.Constants;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ClientNetworkingImpl implements ClientNetworking {
	@Override
	public void fetch(@NotNull String name) {
		RequestStatsPacket packet = new RequestStatsPacket(name, null);
		ClientPlayNetworking.send(Constants.STATS_PACKET_FETCH_CHANNEL, packet);
	}

	@Override
	public void fetch(@NotNull UUID identifier) {
		RequestStatsPacket packet = new RequestStatsPacket(null, identifier);
		ClientPlayNetworking.send(Constants.STATS_PACKET_FETCH_CHANNEL, packet);
	}
}
