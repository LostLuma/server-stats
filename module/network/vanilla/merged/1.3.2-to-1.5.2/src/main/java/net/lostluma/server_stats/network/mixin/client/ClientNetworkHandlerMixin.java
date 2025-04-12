package net.lostluma.server_stats.network.mixin.client;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.common.util.Tuple;
import net.lostluma.server_stats.network.common.PushPacketHelper;
import net.lostluma.server_stats.network.common.SyncPacketHelper;
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
		String channel = packet.channel;

		if (channel.equals(Constants.STATS_PACKET_SMALL_CHANNEL) || channel.equals(Constants.STATS_PACKET_LARGE_CHANNEL)) {
			boolean clear = channel.equals(Constants.STATS_PACKET_SMALL_CHANNEL);

			Map<String, Long> data = SyncPacketHelper.parse(packet);
			this.minecraft.statHandler.server_stats$persist(data, clear);

			callbackInfo.cancel();
		} else if (channel.equals(Constants.STATS_PACKET_AMEND_CHANNEL)) {
			Tuple<String, Long> data = PushPacketHelper.parse(packet);
			this.minecraft.statHandler.server_stats$add(data.left(), data.right());

			callbackInfo.cancel();
		}
	}
}
