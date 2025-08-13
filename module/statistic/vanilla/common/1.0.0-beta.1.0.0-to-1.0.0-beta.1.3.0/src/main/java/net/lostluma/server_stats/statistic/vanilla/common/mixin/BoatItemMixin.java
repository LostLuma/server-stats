package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatItem.class)
public class BoatItemMixin extends Item {
	protected BoatItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when placing a boat.
	 */
	@Inject(
		method = "startUsing",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/World;isMultiplayer:Z"
		)
	)
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.increment(Statistics.useItem(this.id));
	}
}
