package net.lostluma.server_stats.provider.common.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.minecraft.entity.living.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.Map;

/**
 * PersistentStats proxy implemented on the player.
 * The StatProvider implementation is on LocalPlayerEntity and ServerPlayerEntity respectively.
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PersistentStats, StatProvider {
	/**
	 * Dummy stat provider implementation, to ensure that
	 * client-side actions such as dropping items does not cause crashes.
	 */
	@Override
	public @Nullable PersistentStats server_stats$stats() {
		return null;
	}

	@Override
	public long get(@NotNull ServerStatistic stat) {
		PersistentStats stats = this.server_stats$stats();

		if (stats == null) {
			return 0L;
		} else {
			return stats.get(stat);
		}
	}

	@Override
	public long reset(@NotNull ServerStatistic stat) throws IllegalStateException {
		PersistentStats stats = this.server_stats$stats();

		if (stats == null) {
			return 0L;
		} else {
			return stats.reset(stat);
		}
	}

	@Override
	public long increment(@NotNull ServerStatistic stat, long amount) throws IllegalStateException {
		PersistentStats stats = this.server_stats$stats();

		if (stats == null) {
			return 0L;
		} else {
			return stats.increment(stat, amount);
		}
	}

	@Override
	public void server_stats$save() {
		PersistentStats stats = this.server_stats$stats();

		if (stats != null) {
			stats.server_stats$save();
		}
	}

	@Override
	public void server_stats$close() {
		PersistentStats stats = this.server_stats$stats();

		if (stats != null) {
			stats.server_stats$close();
		}
	}

	@Override
	public @NotNull Map<String, Long> server_stats$values() {
		PersistentStats stats = this.server_stats$stats();

		if (stats == null) {
			return Collections.emptyMap();
		} else {
			return stats.server_stats$values();
		}
	}

	@Override
	public @NotNull String server_stats$serialize(boolean large) {
		PersistentStats stats = this.server_stats$stats();

		if (stats == null) {
			return "{}";
		} else {
			return stats.server_stats$serialize(large);
		}
	}
}
