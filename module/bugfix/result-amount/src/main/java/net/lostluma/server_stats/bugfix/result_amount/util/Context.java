package net.lostluma.server_stats.bugfix.result_amount.util;

import net.minecraft.item.ItemStack;

public class Context {
    public int size;
    public ItemStack menuStack;
    public boolean holdingItem;

    public Context(ItemStack menuStack, boolean holdingItem) {
        this.size = menuStack.size;
        this.menuStack = menuStack;
        this.holdingItem = holdingItem;
    }
}
