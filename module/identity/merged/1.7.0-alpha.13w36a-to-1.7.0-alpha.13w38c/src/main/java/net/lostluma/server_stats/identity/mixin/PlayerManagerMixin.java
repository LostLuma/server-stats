package net.lostluma.server_stats.identity.mixin;

import net.lostluma.server_stats.identity.UUIDHelper;
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
	@Inject(method = "createForLogin", at = @At("HEAD"), cancellable = true)
	private void createForLogin(String name, CallbackInfoReturnable<ServerPlayerEntity> callbackInfo) {
		try {
			UUID uuid = Mojang.fetchUuid(name);
			UUIDHelper.setUuid(name, uuid.toString());
		} catch (IOException e) {
			callbackInfo.setReturnValue(null);
			System.out.println("Rejecting login from " + name + ", failed to fetch UUID!");
		}
	}
}
