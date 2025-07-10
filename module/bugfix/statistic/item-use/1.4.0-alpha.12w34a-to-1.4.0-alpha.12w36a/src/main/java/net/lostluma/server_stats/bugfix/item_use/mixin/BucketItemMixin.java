package net.lostluma.server_stats.bugfix.item_use.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin extends Item {
	protected BucketItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when emptying or filling a bucket.
	 */
	@Inject(
		method = "startUsing",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/Item;)V"
		)
	)
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("awarded") LocalBooleanRef awarded) {
		if (!awarded.get()) {
			awarded.set(true);
			player.incrementStat(Stats.ITEMS_USED[this.id]);
		}
	}
}
