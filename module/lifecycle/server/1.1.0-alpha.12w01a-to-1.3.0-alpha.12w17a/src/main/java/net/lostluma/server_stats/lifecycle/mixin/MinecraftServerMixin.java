package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.WorldGeneratorType;
import net.minecraft.world.storage.WorldStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(WorldStorageSource storageSource, String worldDirName, long seed, WorldGeneratorType generatorType, CallbackInfo callbackInfo) {
		PlayerStatsCache.newInstance(worldDirName);
	}

	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		PlayerStatsCache.closeInstance();
	}
}
