package net.lostluma.server_stats.bugfix.result_amount.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.lostluma.server_stats.bugfix.result_amount.util.Context;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.menu.InventoryMenu;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(InventoryMenu.class)
public class InventoryMenuMixin {
    @Shadow
    public List<InventorySlot> slots;

    /**
     * Store the clicked slot's item count as well as whether the player is already holding an item.
     */
    @Inject(method = "onClickSlot", at = @At("HEAD"))
    private void onClickSlot(int id, int button, boolean quickMove, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("context") LocalRef<Context> reference) {
        InventorySlot slot;

        try {
            slot = this.slots.get(id);
        } catch (IndexOutOfBoundsException ignored) {
            return;
        }

        if (slot == null || slot.getClass() == InventorySlot.class) {
            return;
        }

        ItemStack stack = slot.getStack();

        if (stack != null) {
            reference.set(new Context(stack, player.inventory.getCursorStack() != null));
        }
    }

    /**
     * Update the player's statistics with the amount created should a crafting action have occured.
     */
    @Inject(
        method = "onClickSlot",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/slot/InventorySlot;onStackRemovedByPlayer(Lnet/minecraft/item/ItemStack;)V"
        )
    )
    private void onStackRemovedByPlayer(int id, int button, boolean quickMove, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir, @Share("context") LocalRef<Context> reference) {
        Context context = reference.get();

        if (context != null) {
            int difference;

            if (!context.holdingItem) {
                // Initial click on the crafting output
                // The taken stack is unmodified, unable to diff
                difference = context.size;
            } else {
                // Subsequent click on the crafting output
                // Calculate the difference from before -> after
                difference = context.size - context.menuStack.size;
            }

            player.incrementStat(Stats.ITEMS_CRAFTED[context.menuStack.itemId], difference);
        }
    }
}
