package net.lostluma.server_stats.shared.client.mixin;

import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.client.SyncedStats;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin implements DisplayStats, SyncedStats, StatEventHandler {
	@Shadow
	private Map<Stat, Integer> stats;

	@Unique
	private final Map<String, Long> server_stats$stats = new HashMap<>();

	// DisplayStats

	@Override
	public long get(@NotNull ServerStatistic stat) {
		String key = this.server_stats$key(stat);
		return this.server_stats$stats.getOrDefault(key, 0L);
	}

	// SyncedStats

	@Override
	public void server_stats$reset(@NotNull String key) {
		ServerStatisticImpl stat = RegistryImpl.byKey(key);

		if (stat != null) {
			this.server_stats$stats.remove(key);
			this.stats.remove(Stats.byKey(stat.vanillaId()));
		}
	}

	@Override
	public void server_stats$replace(@NotNull PersistentStats override) {
		this.server_stats$persist(override.server_stats$values(), true);
	}

	@Override
	public void server_stats$persist(@NotNull Map<String, Long> overrides, boolean clear) {
		if (clear) {
			this.stats.clear();
			this.server_stats$stats.clear();
		}

		for (Map.Entry<String, Long> entry : overrides.entrySet()) {
			long value = entry.getValue() + this.server_stats$stats.getOrDefault(entry.getKey(), 0L);
			this.server_stats$stats.put(entry.getKey(), value);

			// Also populate vanilla stats map, other mods might read it.
			Stat stat = this.server_stats$getVanillaStat(entry.getKey());

			if (stat != null) {
				if (value <= Integer.MAX_VALUE) {
					this.stats.put(stat, (int) value);
				} else {
					this.stats.put(stat, Integer.MAX_VALUE);
				}
			}
		}
	}

	// StatEventHandler

	@Override
	public void server_stats$reset(@NotNull ServerStatistic stat) {
		this.server_stats$reset(this.server_stats$key(stat));
	}

	@Override
	public void server_stats$push(@NotNull ServerStatistic stat, long value) {
		this.server_stats$add(this.server_stats$key(stat), value);
	}

	// And some other things :3

	@Inject(method = "increment(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
	private void increment(Stat stat, int amount, CallbackInfo callbackInfo) {
		ServerStatisticImpl serverStat = RegistryImpl.byVanillaId(stat.id);

		if (serverStat != null) {
			long value = this.server_stats$stats.getOrDefault(serverStat.key(), 0L);
			this.server_stats$stats.put(serverStat.key(), value + amount);
		}
	}

	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	private void get(Stat stat, CallbackInfoReturnable<Integer> callbackInfo) {
		ServerStatisticImpl modded = RegistryImpl.byVanillaId(stat.id);

		if (modded == null) {
			return;
		}

		Long value = server_stats$stats.get(modded.key());

		if (value == null) {
			callbackInfo.setReturnValue(0);
		} else if (value < Integer.MAX_VALUE) {
			callbackInfo.setReturnValue(value.intValue());
		} else {
			callbackInfo.setReturnValue(Integer.MAX_VALUE);
		}
	}

	@Unique
	private @Nullable Stat server_stats$getVanillaStat(String key) {
		ServerStatisticImpl stat = RegistryImpl.byKey(key);

		if (stat == null || stat.vanillaId() == -1) {
			return null;
		} else {
			return Stats.byKey(stat.vanillaId());
		}
	}

	@Unique
	private @NotNull String server_stats$key(@NotNull ServerStatistic stat) {
		return ((ServerStatisticImpl) stat).key();
	}
}
