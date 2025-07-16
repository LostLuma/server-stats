package net.lostluma.server_stats.bugfix.combat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	public abstract void incrementStat(Stat stat);

	/**
	 * Increment the kill entity stat for any killed living entity.
	 */
	@Inject(method = "onKillEntity", at = @At("RETURN"))
	private void onKillEntity(Entity entity, int score, CallbackInfo callbackInfo) {
		String key = Entities.getKey(entity);
		Stat found = Stats.byKey("stat.killEntity." + key);

		if (found != null) {
			this.incrementStat(found);
		}
	}

	/**
	 * Ignore the original kill entity stat increase, as it only considers mobs with spawn eggs.
	 */
	@WrapOperation(
		method = "onKill",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V",
			ordinal = 1
		)
	)
	private void onKill(PlayerEntity instance, Stat stat, Operation<Void> original) {}
}
