package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ EggItem.class, EnderPearlItem.class, ExperienceBottleItem.class, SnowballItem.class })
public class InstantConsumableItemMixin extends Item {
	protected InstantConsumableItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when consuming an item.
	 */
	@Inject(method = "startUsing", at = @At("RETURN"))
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.incrementStat(Stats.ITEMS_USED[this.id]);
	}
}
