package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
	@Shadow
	@Final
	public int id;

	@Inject(method = "<init>(ILnet/minecraft/block/material/Material;)V", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		ServerStatistic.of("minecraft", "mineBlock." + this.id).build();
	}
}
