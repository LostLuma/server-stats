package net.lostluma.server_stats.broadcast.client.mixin;

import net.lostluma.server_stats.broadcast.client.Broadcast;
import net.minecraft.unmapped.C_5664496;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5664496.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Broadcast.client = (C_5664496)(Object) this;
	}
}
