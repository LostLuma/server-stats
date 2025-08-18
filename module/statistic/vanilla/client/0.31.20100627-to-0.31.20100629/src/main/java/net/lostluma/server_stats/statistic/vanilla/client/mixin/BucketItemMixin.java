package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.lostluma.server_stats.statistic.vanilla.client.util.ItemStackUtil;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		ItemStack copy = ItemStackUtil.copy(stack);
		ItemStack result = original.call(stack, world, player);

		if (!ItemStackUtil.matches(copy, result)) {
			player.increment(Statistics.useItem(this.id));
		}

		return result;
	}
}
