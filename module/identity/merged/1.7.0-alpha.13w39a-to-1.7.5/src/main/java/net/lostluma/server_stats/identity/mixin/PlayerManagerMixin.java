package net.lostluma.server_stats.identity.mixin;

import com.mojang.authlib.GameProfile;
import net.lostluma.server_stats.identity.UUIDHelper;
import net.lostluma.server_stats.util.UUIDUtil;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "createForLogin", at = @At("HEAD"))
	private void createForLogin(GameProfile profile, CallbackInfoReturnable<ServerPlayerEntity> callbackInfo) {
		UUID uuid = UUIDUtil.fromMojang(profile.getId());
		UUIDHelper.setUuid(profile.getName(), uuid.toString());
	}
}
