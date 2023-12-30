package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.Constants;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow
    public List<ServerPlayerEntity> players;

    @Inject(method = "add", at = @At("TAIL"))
    private void onLogin(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        var stats = player.server_stats$getStats();

        if (stats == null) {
            return;
        }

        // Why does this have no proper constructor pre 1.3 ...
        var packet = new CustomPayloadPacket();
        packet.channel = Constants.STATS_PACKET_CHANNEL;
        packet.data = stats.serialize().getBytes(StandardCharsets.UTF_8);;
        packet.size = packet.data.length;

        player.networkHandler.sendPacket(packet);
    }

    @Inject(method = "saveAll", at = @At("TAIL"))
    private void saveAll(CallbackInfo callbackInfo) {
        for (var player : this.players) {
            player.server_stats$saveStats();
        }
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        player.server_stats$saveStats();
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void onRespawn(ServerPlayerEntity player, int dimension, boolean alive, CallbackInfoReturnable<?> callbackInfo) {
        player.server_stats$saveStats();
    }
}
