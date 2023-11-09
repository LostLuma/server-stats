package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.minecraft.world.WorldSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public InputPlayerEntity player;

    @Inject(method = "main", at = @At("HEAD"))
    private static void onMain(CallbackInfo callbackInfo) {
        Stats.init();
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    private void loadWorld(String worldDir, String worldName, WorldSettings worldSettings, CallbackInfo callbackInfo) {
    	ServerPlayerStats.setWorldDirectory(String.format("saves/%s", worldDir));
    }

    // This method is called when the player switches between worlds
    @Inject(method = "m_4977780", at = @At("TAIL"))
    private void onRespawn(int dimension, CallbackInfo callbackInfo) {
    	this.player.server_stats$saveStats();
    }
}
