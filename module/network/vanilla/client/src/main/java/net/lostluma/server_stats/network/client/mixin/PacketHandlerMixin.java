package net.lostluma.server_stats.network.client.mixin;

import net.lostluma.server_stats.common.Constants;
import net.lostluma.server_stats.network.common.CustomPacketHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PacketHandler.class)
public class PacketHandlerMixin {
	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
		if (packet.channel.equals(Constants.STATS_PACKET_CHANNEL)) {
			Map<String, Integer> data = CustomPacketHelper.parse(packet);
			Minecraft.INSTANCE.statHandler.player_stats$override(data);
			callbackInfo.cancel();
		}
	}
}
