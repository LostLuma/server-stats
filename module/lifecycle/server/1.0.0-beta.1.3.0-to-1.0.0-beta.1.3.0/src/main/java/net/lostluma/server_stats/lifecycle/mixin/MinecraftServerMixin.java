package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.common.GameEvent;
import net.lostluma.server_stats.event.common.ServerWorldEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Unique
	private Path path;

	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(WorldStorageSource worldStorageSource, String worldDirName, CallbackInfo callbackInfo) {
		this.path = Paths.get(worldDirName);
		ServerWorldEvent.LOAD.dispatch(new ServerWorldEvent(this.path));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		GameEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		ServerWorldEvent.STOP.dispatch(new ServerWorldEvent(this.path));
	}
}
