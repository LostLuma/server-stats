package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Shadow
	public int itemId;

	@WrapOperation(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;use(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/player/PlayerEntity;Lnet/minecraft/world/World;IIII)Z"
		)
	)
	private boolean use(Item instance, ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int face, Operation<Boolean> original) {
		boolean result = original.call(instance, stack, player, world, x, y, z, face);

		if (result) {
			player.increment(Statistics.useItem(this.itemId));
		}

		return result;
	}
}
