package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ FoodItem.class })
public class InstantConsumableItemMixin extends Item {
	protected InstantConsumableItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when consuming an item.
	 */
	@Inject(method = "startUsing", at = @At("RETURN"))
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.increment(Statistics.useItem(this.id));
	}
}
