package net.lostluma.server_stats.broadcast.client.mixin;

import net.lostluma.server_stats.broadcast.client.Broadcast;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Broadcast.client = (Minecraft)(Object) this;
	}
}
