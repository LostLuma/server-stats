package net.lostluma.server_stats.bugfix.block_merging.mixin;

import net.minecraft.block.Block;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Stats.class)
public class StatsMixin {
	/**
	 * Prevent some statistics from being merged due to being too similar.
	 */
	@Inject(
		method = "mergeBlockStats([Lnet/minecraft/stat/Stat;Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void mergeBlockStats(Stat[] stats, Block from0, Block into, CallbackInfo callbackInfo) {
		int from = Block.getId(from0);

		//  2 -> Grass (usually merged with Dirt)
		// 40 -> Red Mushroom (usually merged with Brown Mushroom)
		// 60 -> Farmland (usually merged with Dirt)
		// 91 -> Jack O' Lantern (usually merged with Pumpkin)
		if (from == 2 || from == 40 || from == 60 || from == 91) {
			callbackInfo.cancel();
		}
	}
}
