package net.lostluma.server_stats.provider.client.mixin;

import net.lostluma.server_stats.api.v1.client.ClientPlayerStats;
import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.api.v1.util.Result;
import net.minecraft.stat.PlayerStats;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin implements DisplayStats {
	@Override
	public long get(@NotNull ServerStatistic stat) {
		Result<DisplayStats, String> result = ClientPlayerStats.get();

		if (!result.isOk()) {
			return 0L;
		} else {
			return result.value().get(stat);
		}
	}
}
