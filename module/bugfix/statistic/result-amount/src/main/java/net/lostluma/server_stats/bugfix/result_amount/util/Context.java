package net.lostluma.server_stats.bugfix.result_amount.util;

import net.minecraft.item.ItemStack;

public class Context {
	public final int size;
	public final ItemStack menuStack;
	public final boolean holdingItem;

	public Context(ItemStack menuStack, boolean holdingItem) {
		this.size = menuStack.size;
		this.menuStack = menuStack;
		this.holdingItem = holdingItem;
	}
}
