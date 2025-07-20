package net.lostluma.server_stats.entrypoint.merged.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import net.lostluma.server_stats.entrypoint.client.ClientModInitializer;
import net.lostluma.server_stats.entrypoint.common.ModInitializer;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Main.class, priority = 2000)
public class MainMixin {
	/**
	 * Invoke the client and general mod entrypoint.
	 * Note: Mixin priority is set to 2000 to run after OSL.
	 */
	@Inject(method = "main", at = @At("HEAD"))
	private static void main(CallbackInfo callbackInfo) {
		FabricLoader.getInstance().invokeEntrypoints(
			ClientModInitializer.KEY,
			ClientModInitializer.class,
			ClientModInitializer::initializeClient
		);

		FabricLoader.getInstance().invokeEntrypoints(
			ModInitializer.KEY,
			ModInitializer.class,
			ModInitializer::initialize
		);
	}
}
