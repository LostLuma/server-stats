package net.lostluma.server_stats.mixin.client;

import net.minecraft.network.packet.CloseMenuPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import net.minecraft.stat.achievement.Achievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	/**
	 * Award the open inventory achievement server-side.
	 * This is not the most accurate, however will roughly track it correctly.
	 */
	@Inject(method = "handleCloseMenu", at = @At("HEAD"))
	private void handleCloseMenu(CloseMenuPacket packet, CallbackInfo callbackInfo) {
		this.player.incrementStat(Achievements.OPEN_INVENTORY);
	}
}
