package net.lostluma.server_stats.bugfix.item_use.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class BucketItemMixin extends Item {
	protected BucketItemMixin(int id) {
		super(id);
	}

	/**
	 * Test whether the bucket was used by seeing whether the stack is modified.
	 */
	@WrapMethod(method = "startUsing")
	private ItemStack startUsing(ItemStack stack, World world, PlayerEntity player, Operation<ItemStack> original) {
		ItemStack copy = stack.copy();
		ItemStack result = original.call(stack, world, player);

		if (!ItemStack.matches(copy, result)) {
			player.incrementStat(Stats.ITEMS_USED[this.id]);
		}

		return result;
	}
}
