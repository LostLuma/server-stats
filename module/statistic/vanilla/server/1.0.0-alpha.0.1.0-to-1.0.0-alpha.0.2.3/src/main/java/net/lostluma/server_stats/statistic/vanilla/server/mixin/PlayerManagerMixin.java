package net.lostluma.server_stats.statistic.vanilla.server.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	/**
	 * Award the Open Inventory achievement when logging in.
	 * <br>
	 * Unfortunately, the Alpha server doesn't get to know when players
	 * actually open and close inventories, so it can't be tracked properly.
	 */
	@Inject(method = "add", at = @At("HEAD"))
	private void add(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		if (!player.isUnlocked(Achievements.OPEN_INVENTORY)) {
			player.unlock(Achievements.OPEN_INVENTORY);
		}
	}

	/**
	 * Keep track of how many times the player has left the server.
	 */
	@Inject(method = "remove", at = @At("HEAD"))
	private void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
		player.increment(Statistics.GAMES_LEFT);
	}
}
