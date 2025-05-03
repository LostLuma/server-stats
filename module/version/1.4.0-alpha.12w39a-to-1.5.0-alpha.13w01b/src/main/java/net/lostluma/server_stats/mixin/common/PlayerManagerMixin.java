package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.common.util.Mojang;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

import java.io.IOException;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "save", at = @At("TAIL"))
	private void onSave(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		player.server_stats$saveStats();
	}

	@Inject(method = "respawn", at = @At("HEAD"))
	private void onRespawn(ServerPlayerEntity player, int dimension, boolean alive, CallbackInfoReturnable<?> callbackInfo) {
		player.server_stats$saveStats();
	}

	@Inject(method = "createForLogin", at = @At("RETURN"), cancellable = true)
	private void createForLogin(CallbackInfoReturnable<ServerPlayerEntity> callbackInfo) {
		ServerPlayerEntity player = callbackInfo.getReturnValue();

		if (player != null) {
			String name = player.server_stats$name();

			try {
				UUID uuid = Mojang.fetchUuid(name);
				player.server_stats$setIdentifier(uuid.toString());
			} catch (IOException e) {
				callbackInfo.setReturnValue(null);
				System.out.println("Rejecting login from " + name + ", failed to fetch UUID!");
			}
		}
	}
}
