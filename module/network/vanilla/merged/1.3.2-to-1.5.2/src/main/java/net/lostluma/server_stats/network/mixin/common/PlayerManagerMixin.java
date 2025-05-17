package net.lostluma.server_stats.network.mixin.common;

import net.lostluma.server_stats.network.common.SyncPacketHelper;
import net.minecraft.network.Connection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "onLogin", at = @At("TAIL"))
	private void onLogin(Connection connection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
		connection.send(SyncPacketHelper.write(player, false));
		connection.send(SyncPacketHelper.write(player, true));
	}
}
