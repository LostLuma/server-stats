package net.lostluma.server_stats.bugfix.result_amount.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.menu.CraftingTableMenu;
import net.minecraft.inventory.menu.FurnaceMenu;
import net.minecraft.inventory.menu.PlayerMenu;
import net.minecraft.inventory.slot.CraftingResultSlot;
import net.minecraft.inventory.slot.FurnaceResultSlot;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = { CraftingTableMenu.class, FurnaceMenu.class, PlayerMenu.class })
public class CraftingMenuMixin {
    /**
     * Store stack size before quick moving items out of a crafting slot.
     */
    @WrapOperation(
        method = "quickMoveStack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/slot/InventorySlot;getStack()Lnet/minecraft/item/ItemStack;"
        )
    )
    private ItemStack getStack(InventorySlot instance, Operation<ItemStack> original, @Share("size") LocalIntRef size) {
        ItemStack stack = original.call(instance);

        // Increase stats only when not moving out of a regular inventory!
        if (stack != null && instance.getClass() != InventorySlot.class) {
            size.set(stack.size);
        }

        return stack;
    }

    /**
     * Calculate difference, and award the player their crafting statistics.
     */
    @WrapOperation(
        method = "quickMoveStack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/slot/InventorySlot;onStackRemovedByPlayer(Lnet/minecraft/item/ItemStack;)V"
        )
    )
    private void onStackRemovedByPlayer(InventorySlot instance, ItemStack stack, Operation<Void> original, @Share("size") LocalIntRef size) {
        original.call(instance, stack);

        PlayerEntity player;

        if (instance instanceof FurnaceResultSlot) {
            player = ((FurnaceResultSlot) instance).player;
        } else if (instance instanceof CraftingResultSlot) {
            player = ((CraftingResultSlot) instance).player;
        } else {
            return; // Regular InventorySlot, isn't crafting
        }

        int difference = size.get() - stack.size;
        player.incrementStat(Stats.ITEMS_CRAFTED[stack.itemId], difference);
    }
}
