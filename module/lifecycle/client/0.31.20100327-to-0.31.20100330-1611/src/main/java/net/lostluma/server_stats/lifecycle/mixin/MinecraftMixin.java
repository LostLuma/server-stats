package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.client.ClientWorldEvent;
import net.lostluma.server_stats.event.common.GameEvent;
import net.lostluma.server_stats.event.common.ServerWorldEvent;
import net.minecraft.client.C_5664496;
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

@Mixin(C_5664496.class)
public class MinecraftMixin {
	@Shadow
	public World f_5854988;

	@Unique
	private @Nullable Path path;

	@Inject(
		method = "m_5272083",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;<init>(Ljava/io/File;Ljava/lang/String;)V"
		)
	)
	private void startGame(String worldDir, CallbackInfo callbackInfo) {
		this.path = Paths.get("saves", worldDir);
		ServerWorldEvent.LOAD.dispatch(new ServerWorldEvent(this.path));
	}

	@Inject(method = "m_8832598", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		GameEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(
		method = "m_9890357",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/C_5664496;f_5854988:Lnet/minecraft/world/World;",
			opcode = Opcodes.PUTFIELD
		)
	)
	private void assignWorld(World world, CallbackInfo callbackInfo) {
		if (world != null && this.f_5854988 == null) {
			ClientWorldEvent.JOIN.dispatch(Event.EMPTY);
		} else if (world == null && this.f_5854988 != null) {
			ClientWorldEvent.LEFT.dispatch(Event.EMPTY);

			if (this.path != null) {
				ServerWorldEvent.STOP.dispatch(new ServerWorldEvent(this.path));
				this.path = null;
			}
		}
	}
}
