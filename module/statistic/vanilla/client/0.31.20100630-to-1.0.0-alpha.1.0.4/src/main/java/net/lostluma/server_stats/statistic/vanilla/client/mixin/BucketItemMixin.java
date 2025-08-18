package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
		ref.set(stack.split(0)); // Budget copy() :3
	}

	/**
	 * Test whether an item was consumed by comparing the {@code ItemStack} to before.
	 */
	@Inject(method = "startUsing", at = @At("RETURN"))
	private void startUsing1(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("stack") LocalRef<ItemStack> ref) {
		if (!this.matches(ref.get(), callbackInfo.getReturnValue())) {
			player.increment(Statistics.useItem(this.id));
		}
	}

	@Unique
	private boolean matches(ItemStack left, ItemStack right) {
		if (left.size != right.size) {
			return false;
		} else if (left.itemId != right.itemId) {
			return false;
		} else {
			return left.metadata == right.metadata;
		}
	}
}
