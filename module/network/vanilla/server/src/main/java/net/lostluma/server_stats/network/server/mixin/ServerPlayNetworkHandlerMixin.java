package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.network.common.PushPacketHelper;
import net.lostluma.server_stats.network.common.ZeroPacketHelper;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements StatEventHandler {
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
}
