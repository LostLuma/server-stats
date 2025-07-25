package net.lostluma.server_stats.lifecycle.mixin.client;

import net.lostluma.server_stats.event.Event;
import net.lostluma.server_stats.event.client.ClientWorldEvent;
import net.lostluma.server_stats.event.common.GameEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public ClientWorld world;

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		GameEvent.TICK.dispatch(Event.EMPTY);
	}

	@Inject(
		method = "setWorld(Lnet/minecraft/client/world/ClientWorld;Ljava/lang/String;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/Minecraft;world:Lnet/minecraft/client/world/ClientWorld;",
			opcode = Opcodes.PUTFIELD
		)
	)
	private void assignWorld(ClientWorld world, String title, CallbackInfo ci) {
		if (world != null && this.world == null) {
			ClientWorldEvent.JOIN.dispatch(Event.EMPTY);
		} else if (world == null && this.world != null) {
			ClientWorldEvent.LEFT.dispatch(Event.EMPTY);
		}
	}
}
