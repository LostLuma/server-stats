package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.client.Vanilla;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.client.gui.screen.inventory.menu.SurvivalInventoryScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurvivalInventoryScreen.class)
public class SurvivalInventoryScreenMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(Inventory inventory, ItemStack[] itemStacks, CallbackInfo callbackInfo) {
		Vanilla.minecraft.player.unlock(Achievements.OPEN_INVENTORY);
	}
}
