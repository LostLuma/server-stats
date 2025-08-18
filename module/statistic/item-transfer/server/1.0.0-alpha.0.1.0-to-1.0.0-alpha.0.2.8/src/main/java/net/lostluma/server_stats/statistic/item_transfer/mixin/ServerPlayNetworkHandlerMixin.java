package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.network.packet.AddItemPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	private ServerPlayerEntity player;

	@Inject(method = "handleAddItem", at = @At("RETURN"))
	private void handlePlayerMove(AddItemPacket packet, CallbackInfo callbackInfo) {
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "drop." + packet.itemId);

		if (this.player.isAlive() && statistic.isPresent()) {
			player.increment(statistic.get(), packet.stackSize);
		}
	}
}
