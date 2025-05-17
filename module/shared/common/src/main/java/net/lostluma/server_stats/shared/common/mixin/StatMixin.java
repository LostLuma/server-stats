package net.lostluma.server_stats.shared.common.mixin;

import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.stat.Stat;
import net.minecraft.stat.achievement.AchievementStat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stat.class)
public class StatMixin {
	@Shadow
	@Final
	public int id;

	@Inject(method = "register", at = @At("RETURN"))
	private void register(CallbackInfoReturnable<Stat> callbackInfo) {
		Stat self = (Stat)(Object) this;

		if (self instanceof AchievementStat) {
			int parentId = -1;
			AchievementStat parent = ((AchievementStat) self).parent;

			if (parent != null) {
				parentId = parent.id;
			}

			RegistryImpl.createVanillaAchievement(this.id, parentId);
		} else {
			RegistryImpl.createVanillaStat(this.id);
		}
	}
}
