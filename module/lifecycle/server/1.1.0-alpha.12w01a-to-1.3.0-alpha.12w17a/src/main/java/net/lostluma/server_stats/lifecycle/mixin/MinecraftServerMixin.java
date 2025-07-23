package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.common.GameEvent;
import net.lostluma.server_stats.event.common.ServerWorldEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.WorldGeneratorType;
import net.minecraft.world.storage.WorldStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Shadow
	public abstract String getWorldDirName();

	@Inject(method = "loadWorld", at = @At("HEAD"))
	private void loadWorld(WorldStorageSource storageSource, String worldDirName, long seed, WorldGeneratorType generatorType, CallbackInfo callbackInfo) {
		Path path = Paths.get(this.getWorldDirName());
		ServerWorldEvent.LOAD.dispatch(new ServerWorldEvent(path));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		GameEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(method = "stop", at = @At("RETURN"))
	private void stop(CallbackInfo callbackInfo) {
		Path path = Paths.get(this.getWorldDirName());
		ServerWorldEvent.STOP.dispatch(new ServerWorldEvent(path));
	}
}
