package net.lostluma.server_stats.statistic.item_transfer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Unique
	private static final ServerStatistic PICKUP = ServerStatistic.of("minecraft", "pickup").build();

	/**
	 * Record the combined and per-item pickup statistic.
	 */
	@WrapOperation(
		method = "onPlayerCollision",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z")
	)
	private boolean onPlayerCollision(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PlayerEntity player) {
		int size = stack.size;
		int itemId = Item.getId(stack.getItem());

		boolean result = original.call(instance, stack);

		if (size != stack.size) {
			int difference = size - stack.size;

			player.increment(PICKUP, difference);
			ServerStatistic.get("minecraft", "pickup." + itemId).ifPresent(stat -> player.increment(stat, difference));
		}

		return result;
	}
}
