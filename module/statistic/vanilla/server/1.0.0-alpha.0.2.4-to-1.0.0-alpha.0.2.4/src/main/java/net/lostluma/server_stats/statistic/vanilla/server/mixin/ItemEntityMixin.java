package net.lostluma.server_stats.statistic.vanilla.server.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@WrapOperation(
		method = "onPlayerCollision",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
		)
	)
	private boolean onPlayerCollision(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PlayerEntity player) {
		boolean result = original.call(instance, stack);

		if (result) {
			if (stack.itemId == Block.LOG.id) {
				player.unlock(Achievements.GET_LOG);
			}

			if (stack.itemId == Item.LEATHER.id) {
				player.unlock(Achievements.KILL_COW);
			}
		}

		return result;
	}
}
