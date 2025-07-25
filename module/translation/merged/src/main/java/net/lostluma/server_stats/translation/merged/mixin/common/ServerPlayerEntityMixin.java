package net.lostluma.server_stats.translation.merged.mixin.common;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Unique
	private ServerPlayerEntity getPlayer() {
		return (ServerPlayerEntity)(Object) this;
	}

	/**
	 * Count statistics server-side.
	 */
	@Inject(method = "incrementStat", at = @At("HEAD"))
	private void incrementStat(Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
		if (vanillaStat == null) {
			return;
		}

		ServerStatistic stat = RegistryImpl.byVanillaId(vanillaStat.id);

		if (stat == null) {
			return;
		}

		if (this.server_stats$parentEarned(stat)) {
			this.getPlayer().increment(stat, amount);
		}
	}

	/**
	 * Whether the parent achievement has been earned, if an achievement is passed.
	 */
	@Unique
	private boolean server_stats$parentEarned(ServerStatistic stat) {
		if (!(stat instanceof ServerAchievement)) {
			return true;
		} else {
			Optional<ServerAchievement> parent = ((ServerAchievement) stat).parent();
			return !parent.isPresent() || this.getPlayer().isUnlocked(parent.get());
		}
	}
}
