package net.lostluma.server_stats.provider.merged.mixin.common;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.util.convert.IntoServerStatisticExt;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Stat.class)
public class StatMixin implements IntoServerStatisticExt {
	@Shadow
	@Final
	public int id;

	@Override
	public ServerStatistic server_stats$into() {
		ServerStatistic statistic = RegistryImpl.byVanillaId(this.id);

		if (statistic != null) {
			return statistic;
		} else {
			throw new NullPointerException("No statistic with id " + id + " is registered.");
		}
	}
}
