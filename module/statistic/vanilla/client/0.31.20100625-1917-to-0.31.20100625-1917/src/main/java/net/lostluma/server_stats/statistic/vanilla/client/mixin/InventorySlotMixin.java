package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.client.duck.DuckInventorySlot;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.unmapped.C_4065940;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventorySlot.class)
public class InventorySlotMixin implements DuckInventorySlot {
	@Unique
	private boolean isResultSlot;

	@Override
	public boolean server_stats$isResultSlot() {
		return this.isResultSlot || (Object) this instanceof C_4065940;
	}

	@Override
	public void server_stats$markResultSlot() {
		this.isResultSlot = true;
	}

	/**
	 * Disallow putting items into this slot, if it is a result slot.
	 */
	@Inject(method = "canSetStack", at = @At("HEAD"), cancellable = true)
	private void canSetStack(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (this.isResultSlot) {
			callbackInfo.setReturnValue(false);
		}
	}
}
