package net.lostluma.server_stats.lifecycle.mixin.client;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.common.ServerWorldEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(CallbackInfo callbackInfo) {
		MinecraftServer self = (MinecraftServer)(Object) this;

		Path path = Paths.get("saves", self.getWorldDirName());
		ServerWorldEvent.LOAD.dispatch(new ServerWorldEvent(path));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		ServerWorldEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		MinecraftServer self = (MinecraftServer)(Object) this;

		Path path = Paths.get("saves", self.getWorldDirName());
		ServerWorldEvent.STOP.dispatch(new ServerWorldEvent(path));
	}
}
