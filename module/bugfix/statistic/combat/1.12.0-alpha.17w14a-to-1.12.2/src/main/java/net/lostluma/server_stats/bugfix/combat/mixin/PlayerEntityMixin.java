package net.lostluma.server_stats.bugfix.combat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	public abstract void incrementStat(Stat stat);

	/**
	 *
	 * Ignore the original kill entity stat increase, as it only considers mobs with spawn eggs.
	 */
	@WrapOperation(
		method = "onKill",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"
		)
	)
	private void onKill(PlayerEntity instance, Stat stat, Operation<Void> original) {}
}
