package net.lostluma.server_stats.bugfix.result_amount.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	/**
	 * Remove Minecraft's code for incrementing player crafting statistics.
	 * Instead of adding the proper amount if always adds the complete size of the stack the player is holding.
	 */
	@WrapOperation(
		method = "onResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"
		)
	)
	private void incrementStat(PlayerEntity instance, Stat amount, int i, Operation<Void> original) {
	}
}
