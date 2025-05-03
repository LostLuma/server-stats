package net.lostluma.server_stats.mixin.client;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "remove", at = @At("HEAD"))
	private void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		player.incrementStat(Stats.GAMES_LEFT);
	}
}
