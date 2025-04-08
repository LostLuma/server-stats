package net.lostluma.server_stats.network.server.mixin;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.network.common.CustomPacketHelper;
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
		ServerPlayerStats stats = player.server_stats$getStats();

		if (stats != null) {
			player.networkHandler.sendPacket(CustomPacketHelper.write(stats, false));
			player.networkHandler.sendPacket(CustomPacketHelper.write(stats, true));
		}

	}
}
