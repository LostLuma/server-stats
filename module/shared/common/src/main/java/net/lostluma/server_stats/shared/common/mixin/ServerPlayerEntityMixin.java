package net.lostluma.server_stats.shared.common.mixin;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Unique
	private ServerPlayerEntity getPlayer() {
		return (ServerPlayerEntity)(Object) this;
	}

	@Inject(method = "incrementStat", at = @At("HEAD"))
	private void incrementStat(Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
		if (vanillaStat == null) {
			return;
		}

		ServerStat stat = ServerStats.byVanillaId(vanillaStat.id);
		ServerPlayerStats stats = this.getPlayer().server_stats$getStats();

		if (stat == null || stats == null) {
			return;
		}

		this.getPlayer().server_stats$incrementStat(stat, amount);
	}
}
