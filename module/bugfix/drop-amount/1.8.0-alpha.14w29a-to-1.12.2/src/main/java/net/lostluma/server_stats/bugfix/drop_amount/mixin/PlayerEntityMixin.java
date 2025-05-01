package net.lostluma.server_stats.bugfix.drop_amount.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	protected abstract boolean isDead();

	/**
	 * Ensure the item drop statistic call is made.
	 * In some earlier versions this module supports the variable is inverted.
	 */
	@Inject(
		method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
		at = @At("HEAD")
	)
	private void dropItem0(CallbackInfoReturnable<ItemStack> callbackInfo, @Local(argsOnly = true, ordinal = 1) LocalBooleanRef isAlive) {
		isAlive.set(!this.isDead());
	}

	/**
	 * Record the actual stack size when throwing an item.
	 */
	@WrapOperation(
		method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"
		)
	)
	private void dropItem1(PlayerEntity instance, Stat stat, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
		instance.incrementStat(stat, stack.size);
	}
}
