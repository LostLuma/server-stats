package net.lostluma.server_stats.statistic.combat.mixin.common;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(World world) {
		super(world);
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(Entity entity, CallbackInfo callbackInfo) {
		if (entity instanceof PlayerEntity) {
			String type = Entities.getKey(this);
			PlayerEntity player = (PlayerEntity) entity;

			ServerStatistic.get("minecraft", "killEntity." + type).ifPresent(player::increment);
		}
	}
}
