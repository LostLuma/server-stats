package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.passive.animal.PigEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(Entity entity, CallbackInfo callbackInfo) {
		if (entity instanceof PlayerEntity) {
			((PlayerEntity) entity).unlock(Achievements.KILL_ENEMY);
		}
	}

	@Inject(method = "damage", at = @At("HEAD"))
	private void damage(Entity source, int amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (source instanceof PlayerEntity) {
			((PlayerEntity) source).increment(Statistics.DAMAGE_DEALT, amount);
		}
	}

	@Inject(method = "applyFallDamage", at = @At("HEAD"))
	private void applyFallDamage(float distance, CallbackInfo callbackInfo) {
		LivingEntity self = (LivingEntity)(Object) this;

		if (self instanceof PigEntity) {
			PigEntity pig = (PigEntity) self;

			if (distance >= 5.0F && pig.rider instanceof PlayerEntity) {
				((PlayerEntity) pig.rider).unlock(Achievements.RIDE_PIG_OFF_CLIFF);
			}
		}
	}
}
