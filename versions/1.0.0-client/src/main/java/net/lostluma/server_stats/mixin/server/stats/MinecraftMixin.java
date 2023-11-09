package net.lostluma.server_stats.mixin.server.stats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	public InputPlayerEntity player;
    
    @Inject(method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/living/player/PlayerEntity;)V", at = @At("HEAD"))
    private void detectWorldLeaving(World world, String string, PlayerEntity playerEntity, CallbackInfo callbackInfo) {
        if (world == null && this.player != null) {
            this.player.server_stats$incrementStat(Stats.GAMES_LEFT, 1);
        }
    }
}
