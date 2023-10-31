package net.lostluma.server_stats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

import java.util.List;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow
    public List<ServerPlayerEntity> players;

    @Inject(method = "saveAll", at = @At("TAIL"))
    private void saveAll(CallbackInfo callbackInfo) {
        for (var player : this.players) {
            player.server_stats$getStats().save();
        }
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        player.server_stats$getStats().save();
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void onRespawn(ServerPlayerEntity player, int dimension, boolean alive, CallbackInfoReturnable<?> callbackInfo) {
        player.server_stats$getStats().save();
    }
}
