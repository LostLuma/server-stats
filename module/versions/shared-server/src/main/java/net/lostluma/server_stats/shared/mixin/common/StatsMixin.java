package net.lostluma.server_stats.shared.mixin.common;

import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Stats.class)
public class StatsMixin {
	@Inject(method = "mergeBlockStats([Lnet/minecraft/stat/Stat;II)V", at = @At("RETURN"))
	private static void mergeBlockStats(Stat[] stats, int blockId1, int blockId2, CallbackInfo ci) {
		ServerStats.mergeStats(blockId1, blockId2);
	}
}
