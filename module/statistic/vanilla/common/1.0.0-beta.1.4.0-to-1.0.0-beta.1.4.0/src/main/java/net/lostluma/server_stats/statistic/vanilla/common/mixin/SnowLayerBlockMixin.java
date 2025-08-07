package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.Block;
import net.minecraft.block.SnowLayerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin extends Block {
	protected SnowLayerBlockMixin(int id, Material material) {
		super(id, material);
	}

	@Inject(method = "afterMinedByPlayer", at = @At("RETURN"))
	private void afterMinedByPlayer(World world, PlayerEntity player, int x, int y, int z, int metadata, CallbackInfo callbackInfo) {
		player.increment(Statistics.mineBlock(this.id));
	}
}
