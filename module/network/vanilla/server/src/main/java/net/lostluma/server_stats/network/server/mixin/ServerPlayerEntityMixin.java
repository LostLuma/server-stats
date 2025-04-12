package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.common.duck.DuckPlayer;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.network.common.PushPacketHelper;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements DuckPlayer {
	@Shadow
	public ServerPlayNetworkHandler networkHandler;

	@Override
	public void server_stats$push(@NotNull ServerStat stat, long value) {
		this.networkHandler.sendPacket(PushPacketHelper.write(stat, value));
	}
}
