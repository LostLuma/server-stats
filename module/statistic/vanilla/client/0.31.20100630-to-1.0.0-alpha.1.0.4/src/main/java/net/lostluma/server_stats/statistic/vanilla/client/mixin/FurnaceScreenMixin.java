package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.inventory.menu.FurnaceScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(FurnaceScreen.class)
public class FurnaceScreenMixin {
	/**
	 * Mark furnace output slots as result slots, preventing putting items into them.
	 */
	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
			ordinal = 2
		)
	)
	private boolean init(List<InventorySlot> instance, Object slot, Operation<Boolean> original) {
		((InventorySlot) slot).server_stats$markResultSlot();
		return original.call(instance, slot);
	}
}
