package net.lostluma.server_stats.shared.client.mixin;

import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.entity.living.player.LocalPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.achievement.AchievementStat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InputPlayerEntity.class, LocalPlayerEntity.class})
public class LocalPlayerEntityMixin {
	@Unique
	private PlayerEntity getPlayer() {
		return (PlayerEntity)(Object) this;
	}

	@Inject(method = "incrementStat(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
	private void incrementStat(net.minecraft.stat.Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
		if (vanillaStat == null) {
			return;
		}

		ServerStat stat = ServerStats.byVanillaId(vanillaStat.id);
		ServerPlayerStats stats = this.getPlayer().server_stats$getStats();

		if (stat == null || stats == null) {
			return;
		}

		if (!this.server_stats$parentEarned(vanillaStat, stats)) {
			return;
		}

		this.getPlayer().server_stats$incrementStat(stat, amount);
	}

	/**
	 * Whether the parent achievement has been earned, if an achievement is passed.
	 */
	@Unique
	private boolean server_stats$parentEarned(Stat stat, ServerPlayerStats stats) {
		if (!(stat instanceof AchievementStat)) {
			return true;
		}

		AchievementStat parent = ((AchievementStat) stat).parent;

		if (parent == null) {
			return true;
		}

		ServerStat serverStat = ServerStats.byVanillaId(parent.id);
		return serverStat != null && stats.get(serverStat) > 0;
	}
}
