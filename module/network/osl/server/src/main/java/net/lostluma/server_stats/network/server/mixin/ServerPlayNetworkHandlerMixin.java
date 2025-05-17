package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.lostluma.server_stats.network.common.PushStatsPacket;
import net.lostluma.server_stats.network.common.ZeroStatsPacket;
import net.lostluma.server_stats.util.Constants;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements StatEventHandler {
	@Shadow
	private ServerPlayerEntity player;

	@Override
	public void server_stats$reset(@NotNull ServerStatistic stat) {
		String key = ((ServerStatisticImpl) stat).key();
		ServerPlayNetworking.send(this.player, Constants.STATS_PACKET_AMEND_CHANNEL, new ZeroStatsPacket(key));
	}

	@Override
	public void server_stats$push(@NotNull ServerStatistic stat, long value) {
		String key = ((ServerStatisticImpl) stat).key();
		ServerPlayNetworking.send(this.player, Constants.STATS_PACKET_AMEND_CHANNEL, new PushStatsPacket(key, value));
	}
}
