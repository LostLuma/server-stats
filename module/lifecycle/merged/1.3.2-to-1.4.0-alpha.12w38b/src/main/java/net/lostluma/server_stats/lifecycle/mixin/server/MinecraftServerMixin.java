package net.lostluma.server_stats.lifecycle.mixin.server;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(CallbackInfo callbackInfo) {
		MinecraftServer server = (MinecraftServer) (Object) this;
		PlayerStatsCache.newInstance(server.getWorldDirName());
	}
}
