package net.lostluma.server_stats.shared.mixin.server;

import net.minecraft.network.packet.MenuClickSlotPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import net.minecraft.stat.achievement.Achievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	private ServerPlayerEntity player;

	@Unique
	private int server_stats$menuId = -1;

	/**
	 * Award the open inventory achievement server-side.
	 * This is not the most accurate, however will roughly track it correctly.
	 */
	@Inject(method = "handleMenuClickSlot", at = @At("HEAD"))
	private void handleMenuClickSlot(MenuClickSlotPacket packet, CallbackInfo callbackInfo) {
		if (packet.menuId == this.server_stats$menuId) {
			return;
		}

		this.server_stats$menuId = packet.menuId;
		this.player.incrementStat(Achievements.OPEN_INVENTORY);
	}
}
