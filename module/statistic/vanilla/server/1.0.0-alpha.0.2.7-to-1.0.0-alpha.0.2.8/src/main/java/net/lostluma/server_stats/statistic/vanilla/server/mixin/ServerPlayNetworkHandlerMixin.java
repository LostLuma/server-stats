package net.lostluma.server_stats.statistic.vanilla.server.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.network.packet.AddItemPacket;
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

	@Inject(method = "handleAddItem", at = @At("RETURN"))
	private void handlePlayerMove(AddItemPacket packet, CallbackInfo callbackInfo) {
		if (this.player.isAlive()) {
			this.player.increment(Statistics.DROPS, packet.stackSize);
		}
	}
}
