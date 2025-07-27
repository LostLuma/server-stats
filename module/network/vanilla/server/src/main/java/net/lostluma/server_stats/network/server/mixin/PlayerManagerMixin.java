package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.network.common.SyncPacketHelper;
import net.lostluma.server_stats.network.common.VersionPacketHelper;
import net.lostluma.server_stats.util.Constants;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "add", at = @At("TAIL"))
	private void onLogin(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		player.networkHandler.sendPacket(VersionPacketHelper.write(Constants.MOD_VERSION));
		player.networkHandler.sendPacket(SyncPacketHelper.write(player, false));
		player.networkHandler.sendPacket(SyncPacketHelper.write(player, true));
	}
}
