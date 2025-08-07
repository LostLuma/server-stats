package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.client.gui.screen.inventory.menu.SurvivalInventoryScreen;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurvivalInventoryScreen.class)
public class SurvivalInventoryScreenMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(PlayerEntity player, CallbackInfo callbackInfo) {
		player.unlock(Achievements.OPEN_INVENTORY);
	}
}
