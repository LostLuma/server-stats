package net.lostluma.server_stats.statistic.movement.mixin;

import net.lostluma.server_stats.statistic.movement.Constants;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Stats.class)
public class StatsMixin {
	@Inject(method="<clinit>", at = @At("RETURN"))
	private static void registerStats(CallbackInfo callbackInfo) {
		Constants.load();
	}
}
