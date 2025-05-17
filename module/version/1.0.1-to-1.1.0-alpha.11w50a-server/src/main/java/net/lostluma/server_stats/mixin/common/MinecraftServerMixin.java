package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		PlayerStatsCache.closeInstance();
	}
}
