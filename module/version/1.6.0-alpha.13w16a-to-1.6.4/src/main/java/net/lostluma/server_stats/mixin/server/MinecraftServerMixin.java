package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Shadow
	public abstract String getWorldDirName();

	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(CallbackInfo callbackInfo) {
		PlayerStatsCache.newInstance(this.getWorldDirName());
	}
}
