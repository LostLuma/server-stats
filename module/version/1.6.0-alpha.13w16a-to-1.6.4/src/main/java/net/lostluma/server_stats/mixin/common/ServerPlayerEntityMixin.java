package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.entity.Entities;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.entity.living.player.ServerPlayerEntity;

@Mixin({PlayerEntity.class, ServerPlayerEntity.class})
public class ServerPlayerEntityMixin {
	@Unique
	private PlayerEntity getPlayer() {
		return (PlayerEntity)(Object) this;
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
		if (source.getAttacker() != null) {
			String type = Entities.getKey(source.getAttacker());
			this.getPlayer().server_stats$incrementStat(ServerStats.getKilledByEntityStat(type), 1);
		}

		this.getPlayer().server_stats$incrementStat(ServerStats.DEATHS, 1);
	}
}
