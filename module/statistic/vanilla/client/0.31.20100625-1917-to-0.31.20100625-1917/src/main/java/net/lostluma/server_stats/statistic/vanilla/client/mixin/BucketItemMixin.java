package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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
		ItemStack copy = stack.split(0); // Budget copy() :3
		ItemStack result = original.call(stack, world, player);

		if (!this.matches(copy, result)) {
			player.increment(Statistics.useItem(this.id));
		}

		return result;
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
