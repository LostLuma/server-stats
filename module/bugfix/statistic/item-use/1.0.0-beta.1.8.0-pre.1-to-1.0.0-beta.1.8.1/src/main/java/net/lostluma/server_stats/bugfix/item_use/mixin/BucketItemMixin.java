package net.lostluma.server_stats.bugfix.item_use.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
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
	 * Store the {@code ItemStack} the player is holding before it is modified.
	 */
	@Inject(method = "startUsing", at = @At("HEAD"))
	private void startUsing0(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("stack") LocalRef<ItemStack> ref) {
		ref.set(stack.copy());
	}

	/**
	 * Test whether an item was consumed by comparing the {@code ItemStack} to before.
	 */
	@Inject(method = "startUsing", at = @At("RETURN"))
	private void startUsing1(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("stack") LocalRef<ItemStack> ref) {
		if (!ItemStack.matches(ref.get(), callbackInfo.getReturnValue())) {
			player.incrementStat(Stats.ITEMS_USED[this.id]);
		}
	}
}
