package net.lostluma.server_stats.shared.client.mixin;

import net.lostluma.server_stats.common.duck.DuckStats;
import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.Stat;
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
public class PlayerStatsMixin implements DuckStats {
	@Shadow
	private Map<Stat, Integer> stats;

	@Unique
	private final Map<String, Long> server_stats$stats = new HashMap<>();

	@Override
	public long server_stats$value(ServerStat stat) {
		return this.server_stats$stats.getOrDefault(stat.key, 0L);
	}

	@Override
	public void server_stats$replace(ServerPlayerStats override) {
		this.server_stats$persist(override.getRawStats(), true);
	}

	@Override
	public void server_stats$persist(Map<String, Long> overrides, boolean clear) {
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

	@Inject(method = "increment(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
	private void increment(Stat stat, int amount, CallbackInfo callbackInfo) {
		ServerStat serverStat = ServerStats.byVanillaId(stat.id);

		if (serverStat != null) {
			long value = this.server_stats$stats.getOrDefault(serverStat.key, 0L);
			this.server_stats$stats.put(serverStat.key, value + amount);
		}
	}

	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	private void get(Stat stat, CallbackInfoReturnable<Integer> callbackInfo) {
		ServerStat modded = ServerStats.byVanillaId(stat.id);

		if (modded == null) {
			return;
		}

		Long value = server_stats$stats.get(modded.key);

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
		ServerStat stat = ServerStats.byKey(key);

		if (stat == null || stat.vanillaId == null) {
			return null;
		} else {
			return net.minecraft.stat.Stats.byKey(stat.vanillaId);
		}
	}
}
