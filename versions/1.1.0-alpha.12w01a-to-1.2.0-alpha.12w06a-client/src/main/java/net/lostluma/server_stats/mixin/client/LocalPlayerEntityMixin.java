package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.stats.Stat;
import net.lostluma.server_stats.stats.Stats;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.entity.living.player.LocalPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ InputPlayerEntity.class, LocalPlayerEntity.class })
public class LocalPlayerEntityMixin {
    private PlayerEntity getPlayer() {
        return (PlayerEntity) (Object) this;
    }

    @Inject(method = "incrementStat(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
    private void incrementStat(net.minecraft.stat.Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
        if (vanillaStat == null) {
            return;
        }

        Stat stat = Stats.byVanillaId(vanillaStat.id);

        if (stat != null) {
            this.getPlayer().server_stats$incrementStat(stat, amount);
        }
    }
}
