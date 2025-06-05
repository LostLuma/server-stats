package net.lostluma.server_stats.item_transfer.mixin;

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
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	/**
	 * Record the per-item pickup statistic.
	 */
	@WrapOperation(
		method = "onPlayerCollision",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z")
	)
	private boolean onPlayerCollision(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) PlayerEntity player) {
		int size = stack.size;

		boolean result = original.call(instance, stack);
		String identifier = Item.REGISTRY.getKey(stack.getItem()).toString().replace(":", ".");
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "pickup." + identifier);

		if (result && statistic.isPresent()) {
			player.increment(statistic.get(), size - stack.size);
		}

		return result;
	}
}
