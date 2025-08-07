package net.lostluma.server_stats.statistic.vanilla.server.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.network.packet.CloseMenuPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	private ServerPlayerEntity player;

	/**
	 * Award the open inventory achievement when closing any menu.
	 */
	@Inject(method = "handleCloseMenu", at = @At("HEAD"))
	private void handleCloseMenu(CloseMenuPacket packet, CallbackInfo callbackInfo) {
		this.player.unlock(Achievements.OPEN_INVENTORY);
	}
}
