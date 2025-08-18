package net.lostluma.server_stats.statistic.item_transfer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.Constants;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Unique
	private static final ServerStatistic PICKUP = ServerStatistic.of(Constants.MOD_ID, "pickup").build();

	/**
	 * Record the combined pickup statistic.
	 */
	@WrapOperation(
		method = "onPlayerCollision",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
		)
	)
	private boolean onPlayerCollision(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PlayerEntity player) {
		int size = stack.size;
		boolean result = original.call(instance, stack);

		if (size != stack.size) {
			player.increment(PICKUP, size - stack.size);
		}

		return result;
	}
}
