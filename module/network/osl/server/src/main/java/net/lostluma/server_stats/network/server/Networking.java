package net.lostluma.server_stats.network.server;

import net.lostluma.server_stats.api.v1.player.MutableStats;
import net.lostluma.server_stats.api.v1.server.ServerPlayerStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.entrypoint.server.ServerModInitializer;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.network.common.RequestStatsPacket;
import net.lostluma.server_stats.network.common.Versionpacket;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.network.common.SyncStatsPacket;
import net.lostluma.server_stats.util.Logging;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.ornithemc.osl.networking.api.server.ServerConnectionEvents;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class Networking implements ServerModInitializer {
	@Override
	public void initializeServer() {
		// Send player's own stats to them on login
		ServerConnectionEvents.PLAY_READY.register((server, player) -> {
			ServerPlayNetworking.send(player, Constants.PROTOCOL_BROADCAST_CHANNEL, new Versionpacket(Constants.MOD_VERSION));
			ServerPlayNetworking.send(player, Constants.STATS_PACKET_SMALL_CHANNEL, new SyncStatsPacket(player, false));
			ServerPlayNetworking.send(player, Constants.STATS_PACKET_LARGE_CHANNEL, new SyncStatsPacket(player, true));
		});

		// Respond to stats query packets for other player's stats
		ServerPlayNetworking.registerListener(Constants.STATS_PACKET_FETCH_CHANNEL, RequestStatsPacket::new, (server, handler, player, payload) -> {
			String name = payload.username();
			UUID identifier = payload.identifier();

			if (name != null) {
				ServerPlayerStats.fetch(name, result -> this.handleStats(player, name, null, result));
			} else if (identifier != null) {
				ServerPlayerStats.fetch(identifier, result -> this.handleStats(player, null, identifier, result));
			} else {
				Logging.getLogger().warn("Received incomplete stats fetch packet from {}!", player);
			}

			return true;
		});
	}

	private void handleStats(@NotNull ServerPlayerEntity player, @Nullable String name, @Nullable UUID identifier, @NotNull Result<MutableStats, String> result) {
		RequestStatsPacket response;

		if (result.isOk()) {
			PersistentStats persistent = (PersistentStats) result.value();
			Map<String, Long> raw = persistent.server_stats$values();
			response = new RequestStatsPacket(name, identifier, raw);
		} else {
			response = new RequestStatsPacket(name, identifier, result.error());
		}

		ServerPlayNetworking.send(player, Constants.STATS_PACKET_FETCH_CHANNEL, response);
	}
}
