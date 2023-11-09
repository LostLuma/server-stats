package net.lostluma.server_stats.mixin.server;

import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

@Mixin({PlayerEntity.class, ServerPlayerEntity.class })
public class ServerPlayerEntityMixin {
    private PlayerEntity getPlayer() {
        return (PlayerEntity) (Object) this;
    }

    @Inject(method = "incrementStat(Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
    private void incrementStat(net.minecraft.stat.Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
        if (vanillaStat == null) {
            return;
        }

        var stat = Stats.byVanillaId(vanillaStat.id);

        if (stat != null) {
            this.getPlayer().server_stats$incrementStat(stat, amount);
        }
    }

    @Inject(method = "onKilled", at = @At("HEAD"))
    private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
        if (source.getAttacker() != null) {
            var attacker = source.getAttacker();
            this.getPlayer().server_stats$incrementStat(Stats.getKilledByEntityStat(attacker), 1);
        }
    }
}
