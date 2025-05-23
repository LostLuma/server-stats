package net.lostluma.server_stats.combat.mixin.common;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.minecraft.entity.Entities;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Unique
	private PlayerEntity player() {
		return (PlayerEntity) (Object) this;
	}

	@Inject(method = "onKill", at = @At("HEAD"))
	private void onKill(LivingEntity entity, CallbackInfo callbackInfo) {
		String type = Entities.getKey(entity);
		ServerStatistic.get("minecraft", "killEntity." + type).ifPresent(this.player()::increment);
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
		if (source.getAttacker() == null) {
			return;
		}

		String type = Entities.getKey(source.getAttacker());
		ServerStatistic.get("minecraft", "entityKilledBy." + type).ifPresent(this.player()::increment);
	}
}
