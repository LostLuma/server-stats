package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.FireworksItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworksItem.class)
public class FireworksItemMixin extends Item {
	/**
	 * Count item use when using a rocket to gain flight momentum.
	 */
	@Inject(method = "startUsing", at = @At("RETURN"))
	private void startUsing(World world, PlayerEntity player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callbackInfo) {
		InteractionResultHolder<ItemStack> result = callbackInfo.getReturnValue();

		if (result.getResult().equals(InteractionResult.SUCCESS)) {
			player.incrementStat(Stats.itemUsed(this));
		}
	}
}
