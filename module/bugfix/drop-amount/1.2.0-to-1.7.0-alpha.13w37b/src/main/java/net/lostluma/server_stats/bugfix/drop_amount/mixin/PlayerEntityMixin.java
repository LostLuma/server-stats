package net.lostluma.server_stats.bugfix.drop_amount.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	protected abstract boolean isDead();

	/**
	 * Record the actual stack size when throwing an item,
	 * and do not increase the items dropped statistic when dying.
	 */
	@WrapOperation(
		method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V")
	)
	private void dropItem(PlayerEntity instance, Stat stat, int amount, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
		if (!this.isDead()) {
			original.call(instance, stat, stack.size);
		}
	}
}
