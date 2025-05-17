package net.lostluma.server_stats.network.server;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.server.ServerPlayerStatsImpl;
import net.lostluma.server_stats.network.common.RequestStatsPacket;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.network.common.SyncStatsPacket;
import net.lostluma.server_stats.util.Logging;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.ornithemc.osl.entrypoints.api.server.ServerModInitializer;
import net.ornithemc.osl.networking.api.server.ServerConnectionEvents;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class Networking implements ServerModInitializer {
	@Override
	public void initServer() {
		// Send player's own stats to them on login
		ServerConnectionEvents.PLAY_READY.register((server, player) -> {
			ServerPlayNetworking.send(player, Constants.STATS_PACKET_SMALL_CHANNEL, new SyncStatsPacket(player, false));
			ServerPlayNetworking.send(player, Constants.STATS_PACKET_LARGE_CHANNEL, new SyncStatsPacket(player, true));
		});

		// Respond to stats query packets for other player's stats
		ServerPlayNetworking.registerListener(Constants.STATS_PACKET_FETCH_CHANNEL, RequestStatsPacket::new, (server, handler, player, payload) -> {
			String name = payload.username();
			UUID identifier = payload.identifier();

			if (name != null) {
				ServerPlayerStatsImpl.get(name, (stats, error) -> this.handleStats(player, name, null, stats, error));
			} else if (identifier != null) {
				ServerPlayerStatsImpl.get(identifier, (stats, error) -> this.handleStats(player, null, identifier, stats, error));
			} else {
				Logging.getLogger().warn("Received incomplete stats fetch packet from {}!", player);
			}

			return true;
		});
	}

	private void handleStats(@NotNull ServerPlayerEntity player, @Nullable String name, @Nullable UUID identifier, @Nullable MutableStats stats, @Nullable String error) {
		RequestStatsPacket response;

		if (stats != null) {
			PersistentStats persistent = (PersistentStats) stats;
			Map<String, Long> raw = persistent.server_stats$values();
			response = new RequestStatsPacket(name, identifier, raw);
		} else if (error != null) {
			response = new RequestStatsPacket(name, identifier, error);
		} else {
			throw new RuntimeException("unreachable");
		}

		ServerPlayNetworking.send(player, Constants.STATS_PACKET_FETCH_CHANNEL, response);
	}
}
