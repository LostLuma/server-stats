package net.lostluma.server_stats.network.mixin.client;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.network.common.CustomPacketHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientNetworkHandler;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
	@Shadow
	private Minecraft minecraft;

	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
		if (packet.channel.equals(Constants.STATS_PACKET_CHANNEL)) {
			Map<String, Integer> data = CustomPacketHelper.parse(packet);
			this.minecraft.stats.player_stats$override(data);
			callbackInfo.cancel();
		}
	}
}
