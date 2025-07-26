package net.lostluma.server_stats.translation.merged.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.entity.living.player.InputClientPlayerEntity;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.achievement.AchievementStat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputClientPlayerEntity.class)
public class InputPlayerEntityMixin {
	@Unique
	private int ticks;

	/**
     * Delay the Open Inventory achievement tutorial for one minute.
	 * <br>
	 * On servers with Server Stats this avoids the tutorial popping
	 * up on login, because the sync packet comes a few ticks later.
	 */
	@WrapOperation(
		method = "tickAI",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/stat/PlayerStats;hasAchievement(Lnet/minecraft/stat/achievement/AchievementStat;)Z"
		)
	)
	private boolean tickAI(PlayerStats instance, AchievementStat achievement, Operation<Boolean> original) {
		if (this.ticks++ < 1200) {
			return true;
		} else {
			return original.call(instance, achievement);
		}
	}
}
