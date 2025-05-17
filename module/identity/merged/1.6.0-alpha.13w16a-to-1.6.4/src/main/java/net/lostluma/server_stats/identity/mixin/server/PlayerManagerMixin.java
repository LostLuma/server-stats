package net.lostluma.server_stats.identity.mixin.server;

import net.lostluma.server_stats.util.Logging;
import net.lostluma.server_stats.util.Mojang;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "createForLogin", at = @At("RETURN"), cancellable = true)
	private void createForLogin(CallbackInfoReturnable<ServerPlayerEntity> callbackInfo) {
		ServerPlayerEntity player = callbackInfo.getReturnValue();

		if (player != null) {
			String name = player.server_stats$name();

			try {
				UUID uuid = Mojang.fetchUuid(name);
				player.server_stats$setIdentifier(uuid);
			} catch (IOException e) {
				callbackInfo.setReturnValue(null);
				Logging.getLogger().warn("Rejecting login from {}, failed to fetch UUID!", name);
			}
		}
	}
}
