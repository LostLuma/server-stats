package net.lostluma.server_stats.bugfix.pickup_amount.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	/**
	 * Record the per-item pickup statistic when a stack is partially picked up.
	 */
	@WrapOperation(
		method = "onPlayerCollision",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
		)
	)
	private boolean server_stats$record(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PlayerEntity player) {
		int size = stack.size;
		Stat stat = Stats.itemPickedUp(Item.getId(stack.getItem()));

		boolean result = original.call(instance, stack);

		if (!result && size != stack.size) {
			player.incrementStat(stat, size - stack.size);
		}

		return result;
	}
}
