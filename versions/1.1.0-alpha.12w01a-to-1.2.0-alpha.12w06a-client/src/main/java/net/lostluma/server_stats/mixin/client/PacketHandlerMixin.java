package net.lostluma.server_stats.mixin.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Mixin(PacketHandler.class)
public class PacketHandlerMixin {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handleCustomPayload(CustomPayloadPacket packet, CallbackInfo callbackInfo) {
        if (!packet.channel.equals(Constants.STATS_PACKET_CHANNEL)) {
            return;
        }

        var data = new String(packet.data, StandardCharsets.UTF_8);

        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> result = new Gson().fromJson(data, type);

        Minecraft.INSTANCE.statHandler.player_stats$override(result);
        callbackInfo.cancel();
    }
}
