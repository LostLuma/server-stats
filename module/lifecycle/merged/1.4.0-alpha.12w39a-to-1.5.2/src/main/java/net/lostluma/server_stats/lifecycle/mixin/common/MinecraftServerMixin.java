package net.lostluma.server_stats.lifecycle.mixin.common;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.common.GameEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		GameEvent.TICK.dispatch(Event.EMPTY);
	}
}
