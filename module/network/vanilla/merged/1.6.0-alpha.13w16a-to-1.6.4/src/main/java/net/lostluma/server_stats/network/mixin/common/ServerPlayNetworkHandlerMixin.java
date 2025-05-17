package net.lostluma.server_stats.network.mixin.common;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.server.ServerPlayerStatsImpl;
import net.lostluma.server_stats.network.common.PushPacketHelper;
import net.lostluma.server_stats.network.common.RequestPacketHelper;
import net.lostluma.server_stats.network.common.ZeroPacketHelper;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.util.Logging;
import net.minecraft.network.packet.CustomPayloadPacket;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements StatEventHandler {
	@Shadow
	private ServerPlayerEntity player;

	@Shadow
	public abstract void sendPacket(Packet packet);

	@Override
	public void server_stats$reset(@NotNull ServerStatistic stat) {
		this.sendPacket(ZeroPacketHelper.write(stat));
	}

	@Override
	public void server_stats$push(@NotNull ServerStatistic stat, long value) {
		this.sendPacket(PushPacketHelper.write(stat, value));
	}

	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
		if (!packet.channel.equals(Constants.STATS_PACKET_FETCH_CHANNEL)) {
			return;
		}

		RequestPacketHelper.RequestPacketData data = RequestPacketHelper.parse(packet);

		if (data == null) {
			return;
		}

		String name = data.username;
		UUID identifier = data.identifier;

		if (name != null) {
			ServerPlayerStatsImpl.get(name, (stats, error) -> this.handleStats(name, null, stats, error));
		} else if (identifier != null) {
			ServerPlayerStatsImpl.get(identifier, (stats, error) -> this.handleStats(null, identifier, stats, error));
		} else {
			Logging.getLogger().warn("Received incomplete stats fetch packet from {}!", this.player);
		}

		callbackInfo.cancel();
	}

	@Unique
	private void handleStats(@Nullable String name, @Nullable UUID identifier, @Nullable MutableStats stats, @Nullable String error) {
		Map<String, Long> raw = Collections.emptyMap();

		if (stats != null) {
			PersistentStats persistent = (PersistentStats) stats;
			raw = persistent.server_stats$values();
		}

		this.sendPacket(RequestPacketHelper.write(name, identifier, error, raw));
	}
}
