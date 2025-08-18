package net.lostluma.server_stats.statistic.vanilla.client.util;

import net.minecraft.item.ItemStack;

public class ItemStackUtil {
	public static ItemStack copy(ItemStack stack) {
		return new ItemStack(stack.itemId, stack.size, stack.metadata);
	}

	public static boolean matches(ItemStack left, ItemStack right) {
		if (left.size != right.size) {
			return false;
		} else if (left.itemId != right.itemId) {
			return false;
		} else {
			return left.metadata == right.metadata;
		}
	}
}
