package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.client.ClientWorldEvent;
import net.lostluma.server_stats.event.common.ServerWorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public World world;

	@Unique
	private @Nullable Path path;

	@Inject(method = "startGame", at = @At("HEAD"))
	private void startGame(String worldDir, String worldName, long seed, CallbackInfo callbackInfo) {
		this.path = Paths.get("saves", worldDir);
		ServerWorldEvent.LOAD.dispatch(new ServerWorldEvent(this.path));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		ServerWorldEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(
		method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/living/player/PlayerEntity;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/Minecraft;world:Lnet/minecraft/world/World;",
			opcode = Opcodes.PUTFIELD
		)
	)
	private void assignWorld(World world, String message, PlayerEntity player, CallbackInfo callbackInfo) {
		if (world != null && this.world == null) {
			ClientWorldEvent.JOIN.dispatch(Event.EMPTY);
		} else if (world == null && this.world != null) {
			ClientWorldEvent.LEFT.dispatch(Event.EMPTY);

			if (this.path != null) {
				ServerWorldEvent.STOP.dispatch(new ServerWorldEvent(this.path));
				this.path = null;
			}
		}
	}
}
