package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.world.storage.WorldStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(WorldStorageSource worldStorageSource, String worldDirName, long l, CallbackInfo callbackInfo) {
		PlayerStatsCache.newInstance(worldDirName);
	}
}
