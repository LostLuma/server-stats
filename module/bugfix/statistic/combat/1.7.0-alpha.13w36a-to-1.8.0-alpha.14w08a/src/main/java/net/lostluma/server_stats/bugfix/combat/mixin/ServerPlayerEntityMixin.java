package net.lostluma.server_stats.bugfix.combat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entities;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Shadow
	public abstract void incrementStat(Stat stat, int amount);

	/**
	 * Increase the killed by entity stat for any living entity.
	 */
	@Inject(method = "onKilled", at = @At("RETURN"))
	private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
		if (source.getAttacker() == null) {
			return;
		}

		String key = Entities.getKey(source.getAttacker());
		Stat found = Stats.byKey("stat.entityKilledBy." + key);

		if (found != null) {
			this.incrementStat(found, 1);
		}
	}

	/**
	 * Ignore the original kill by entity stat increase, as it only considers mobs with spawn eggs.
	 */
	@WrapOperation(
		method = "onKilled",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/entity/living/player/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V",
			ordinal = 0
		)
	)
	private void onKilled(ServerPlayerEntity instance, Stat stat, int amount, Operation<Void> original) {}
}
