package net.lostluma.server_stats.broadcast.merged.mixin;

import net.lostluma.server_stats.broadcast.merged.Broadcast;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Broadcast.server = (MinecraftServer)(Object) this;
	}

	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		Broadcast.server = null;
	}
}
