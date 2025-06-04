package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "saveAll", at = @At("TAIL"))
	private void saveAll(CallbackInfo callbackInfo) {
		PlayerStatsCache.getInstance().save();
	}

	@Inject(method = "remove", at = @At("TAIL"))
	private void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		player.server_stats$save();
	}

	@Inject(method = "respawn", at = @At("HEAD"))
	private void onRespawn(ServerPlayerEntity player, int dimension, boolean alive, CallbackInfoReturnable<?> callbackInfo) {
		player.server_stats$save();
	}
}
