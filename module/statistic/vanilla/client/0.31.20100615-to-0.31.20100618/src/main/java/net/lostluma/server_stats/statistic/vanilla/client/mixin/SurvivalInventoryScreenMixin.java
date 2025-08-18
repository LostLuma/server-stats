package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.client.Vanilla;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.client.gui.screen.inventory.menu.SurvivalInventoryScreen;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurvivalInventoryScreen.class)
public class SurvivalInventoryScreenMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(Inventory inventory, CallbackInfo callbackInfo) {
		Vanilla.minecraft.f_6058446.unlock(Achievements.OPEN_INVENTORY);
	}
}
