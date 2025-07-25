package net.lostluma.server_stats.provider.common.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.util.convert.IntoServerAchievementExt;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.stat.achievement.AchievementStat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AchievementStat.class)
public class AchievementStatMixin implements IntoServerAchievementExt {
	@Override
	public ServerAchievement server_stats$into() {
		int id = ((AchievementStat)(Object) this).id;
		ServerStatistic statistic = RegistryImpl.byVanillaId(id);

		if (statistic != null) {
			return (ServerAchievement) statistic;
		} else {
			throw new NullPointerException("No achievement with id " + id + " is registered.");
		}
	}
}
