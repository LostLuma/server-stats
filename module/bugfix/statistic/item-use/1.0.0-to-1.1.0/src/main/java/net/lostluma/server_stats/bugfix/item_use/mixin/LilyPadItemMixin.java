package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LilyPadItem;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LilyPadItem.class)
public class LilyPadItemMixin extends Item {
	protected LilyPadItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when placing the lily pad on water.
	 */
	@Inject(
		method = "startUsing",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z"
		)
	)
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.incrementStat(Stats.ITEMS_USED[this.id]);
	}
}
