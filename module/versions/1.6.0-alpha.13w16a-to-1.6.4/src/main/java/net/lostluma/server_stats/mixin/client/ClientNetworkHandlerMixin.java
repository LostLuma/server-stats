package net.lostluma.server_stats.mixin.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientNetworkHandler;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Shadow

    private Minecraft minecraft;

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
        if (!packet.channel.equals(Constants.STATS_PACKET_CHANNEL)) {
            return;
        }

        String data = new String(packet.data, StandardCharsets.UTF_8);

        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> result = new Gson().fromJson(data, type);

        this.minecraft.stats.player_stats$override(result);
        callbackInfo.cancel();
    }
}
