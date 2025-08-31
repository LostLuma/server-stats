package net.lostluma.server_stats.entrypoint.merged.mixin.server;

import net.lostluma.server_stats.entrypoint.common.ModInitializer;
import net.lostluma.server_stats.entrypoint.server.ServerModInitializer;
import net.lostluma.server_stats.util.platform.Platform;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, priority = 2000)
public class MinecraftServerMixin {
	/**
	 * Invoke the server and general mod entrypoint.
	 * Note: Mixin priority is set to 2000 to run after OSL.
	 */
	@Inject(method = "main", at = @At("HEAD"))
	private static void main(CallbackInfo callbackInfo) {
		Platform.invokeEntrypoints(
			ServerModInitializer.KEY,
			ServerModInitializer.class,
			ServerModInitializer::initializeServer
		);

		Platform.invokeEntrypoints(
			ModInitializer.KEY,
			ModInitializer.class,
			ModInitializer::initialize
		);
	}
}
