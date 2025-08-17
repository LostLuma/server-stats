package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.inventory.menu.FurnaceMenu;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceMenu.class)
public class FurnaceMenuMixin {
	/**
	 * Mark furnace output slots as result slots, preventing putting items into them.
	 */
	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/inventory/menu/FurnaceMenu;addSlot(Lnet/minecraft/inventory/slot/InventorySlot;)V",
			ordinal = 2
		)
	)
	private void init(FurnaceMenu instance, InventorySlot slot, Operation<Void> original) {
		slot.server_stats$markResultSlot();
		original.call(instance, slot);
	}
}
