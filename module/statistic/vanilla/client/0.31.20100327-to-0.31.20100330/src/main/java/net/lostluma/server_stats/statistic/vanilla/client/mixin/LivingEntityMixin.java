package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.hostile.HostileEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(Entity source, CallbackInfo callbackInfo) {
		if (!(source instanceof PlayerEntity)) {
			return;
		}

		LivingEntity self = (LivingEntity)(Object) this;
		PlayerEntity player = (PlayerEntity) source;

		if (!(self instanceof PlayerEntity)) {
			player.increment(Statistics.MOBS_KILLED);

			if (self instanceof HostileEntity) {
				player.unlock(Achievements.KILL_ENEMY);
			}
		} else {
			player.increment(Statistics.PLAYERS_KILLED);
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
		if ((Object) this instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)(Object) this;

			if (distance >= 2.0F) {
				player.increment(Statistics.CM_FALLEN, Math.round(distance * 100));
			}
		}
	}
}
