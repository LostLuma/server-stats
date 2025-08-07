package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
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

	@Inject(method = "afterMinedByPlayer", at = @At("RETURN"))
	private void afterMinedByPlayer(World world, PlayerEntity player, int x, int y, int z, int metadata, CallbackInfo callbackInfo) {
		player.increment(Statistics.mineBlock(this.id));
	}
}
