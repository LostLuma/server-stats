package net.lostluma.server_stats.mixin.common.stats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.entity.FishingBobberEntity;
import net.minecraft.entity.living.player.PlayerEntity;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Shadow
    public PlayerEntity player;

    @Inject(method = "retract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void onRetract(CallbackInfoReturnable<?> callbackInfo) {
        this.player.server_stats$incrementStat(Stats.FISH_CAUGHT, 1);
    }
}
