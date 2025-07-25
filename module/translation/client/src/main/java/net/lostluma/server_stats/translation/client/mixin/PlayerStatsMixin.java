package net.lostluma.server_stats.translation.client.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.client.LocalDisplayStatsImpl;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.Stat;
import net.minecraft.stat.achievement.AchievementStat;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin {
	/**
	 * Store statistic updates received via the vanilla system.
	 */
	@Inject(method = "increment(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
	private void increment(Stat stat, int amount, CallbackInfo callbackInfo) {
		LocalDisplayStatsImpl stats = ClientPlayerStatsImpl.getPlayerStats();

		if (stats != null) {
			stats.server_stats$push(ServerStatistic.from(stat), amount);
		}
	}

	/**
	 * Read from the modded statistic cache instead of the vanilla one.
	 */
	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	private void get(Stat stat, CallbackInfoReturnable<Integer> callbackInfo) {
		callbackInfo.setReturnValue(this.getValue(stat));
	}

	/**
	 * Read from the modded statistic cache instead of the vanilla one.
	 */
	@Inject(method = "hasAchievement", at = @At("HEAD"), cancellable = true)
	private void hasAchievement(AchievementStat achievement, CallbackInfoReturnable<Boolean> callbackInfo) {
		callbackInfo.setReturnValue(this.getValue(achievement) != 0);
	}

	@Unique
	private int getValue(@NotNull Stat stat) {
		LocalDisplayStatsImpl stats = ClientPlayerStatsImpl.getPlayerStats();

		// Player is not in a world
		if (stats == null) {
			return 0;
		}

		long value = stats.get(ServerStatistic.from(stat));

		if (value <= Integer.MAX_VALUE) {
			return (int) value;
		} else {
			return Integer.MAX_VALUE;
		}
	}
}
