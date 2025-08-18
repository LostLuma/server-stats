package net.lostluma.server_stats.statistic.vanilla.client.util;

import net.minecraft.item.ItemStack;

public class Context {
	public final int size;
	public final ItemStack menuStack;

	public Context(ItemStack menuStack) {
		this.size = menuStack.size;
		this.menuStack = menuStack;
	}
}
