package net.lostluma.server_stats.combat.mixin.server;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.minecraft.entity.Entities;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Unique
	private PlayerEntity getPlayer() {
		return (PlayerEntity)(Object) this;
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
		ServerStatistic.get("minecraft", "deaths").ifPresent(this.getPlayer()::increment);

		if (source.getAttacker() == null) {
			return;
		}

		String type = Entities.getKey(source.getAttacker());
		ServerStatistic.get("minecraft", "entityKilledBy." + type).ifPresent(this.getPlayer()::increment);
	}
}
