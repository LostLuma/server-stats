package net.lostluma.server_stats.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "save", at = @At("TAIL"))
    private void onSave(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        player.server_stats$saveStats();
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void onRespawn(ServerPlayerEntity player, int dimension, boolean alive, CallbackInfoReturnable<?> callbackInfo) {
        player.server_stats$saveStats();
    }
}
