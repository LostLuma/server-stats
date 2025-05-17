package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.entity.Entities;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements StatProvider {
	@Unique
	private PlayerEntity player() {
		return (PlayerEntity) (Object) this;
	}

	@Inject(method = "onKill", at = @At("HEAD"))
	private void onKill(LivingEntity entity, CallbackInfo callbackInfo) {
		String type = Entities.getKey(entity);
		this.player().increment(RegistryImpl.getEntityKillStat(type), 1);
	}
}
