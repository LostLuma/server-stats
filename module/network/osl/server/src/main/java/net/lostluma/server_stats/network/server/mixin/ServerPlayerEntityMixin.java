package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.common.duck.DuckPlayer;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.network.common.PushStatsPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements DuckPlayer {
	@Override
	public void server_stats$push(@NotNull ServerStat stat, long value) {
		ServerPlayerEntity player = (ServerPlayerEntity)(Object) this;
		ServerPlayNetworking.send(player, Constants.STATS_PACKET_AMEND_CHANNEL, new PushStatsPacket(stat.key, value));
	}
}
