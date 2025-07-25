package net.lostluma.server_stats.network.mixin.client;

import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.network.common.RequestPacketHelper;
import net.lostluma.server_stats.network.common.ZeroPacketHelper;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.util.Tuple;
import net.lostluma.server_stats.network.common.PushPacketHelper;
import net.lostluma.server_stats.network.common.SyncPacketHelper;
import net.minecraft.client.network.handler.ClientNetworkHandler;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
		String channel = packet.channel;

		switch (channel) {
			case Constants.STATS_PACKET_SMALL_CHANNEL:
			case Constants.STATS_PACKET_LARGE_CHANNEL: {
				boolean clear = channel.equals(Constants.STATS_PACKET_SMALL_CHANNEL);

				Map<String, Long> data = SyncPacketHelper.parse(packet);
				ClientPlayerStatsImpl.getPlayerStats().persist(data, clear);
				break;
			}
			case Constants.STATS_PACKET_RESET_CHANNEL: {
				String data = ZeroPacketHelper.parse(packet);
				ClientPlayerStatsImpl.getPlayerStats().reset(data);
			}
			case Constants.STATS_PACKET_AMEND_CHANNEL: {
				Tuple<String, Long> data = PushPacketHelper.parse(packet);
				ClientPlayerStatsImpl.getPlayerStats().add(data.left(), data.right());
				break;
			}
			case Constants.STATS_PACKET_FETCH_CHANNEL: {
				RequestPacketHelper.RequestPacketData data = RequestPacketHelper.parse(packet);

				if (data != null) {
					ClientPlayerStatsImpl.onResponse(data.username, data.identifier, data.values, data.errorMessage);
				}
				break;
			} default: {
				return;
			}
		}

		callbackInfo.cancel();
	}
}
