package net.lostluma.server_stats.mixin.stats.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.block.Block;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin {
    @Inject(method = "afterMinedByPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void onAfterMinedByPlayer(World world, PlayerEntity player, int x, int y, int z, int metadata, CallbackInfo callbackInfo) {
        Block block = (Block)(Object)this;
        player.server_stats$incrementStat(Stats.BLOCKS_MINED[block.id], 1);
    }
}
