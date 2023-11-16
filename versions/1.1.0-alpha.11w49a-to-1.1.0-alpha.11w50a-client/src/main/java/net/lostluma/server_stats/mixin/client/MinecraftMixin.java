package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public InputPlayerEntity player;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo callbackInfo) {
        Stats.init();
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(String worldDir, String worldName, WorldSettings worldSettings, CallbackInfo callbackInfo) {
        ServerPlayerStats.setWorldDirectory(String.format("saves/%s", worldDir));
    }

    @Inject(method = "m_4977780", at = @At("TAIL"))
    private void changeDimension(int dimension, CallbackInfo callbackInfo) {
        this.player.server_stats$saveStats();
    }
}
