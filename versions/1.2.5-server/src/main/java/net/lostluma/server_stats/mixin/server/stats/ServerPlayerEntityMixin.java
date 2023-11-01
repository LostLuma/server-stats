package net.lostluma.server_stats.mixin.server.stats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "onKilled", at = @At("HEAD"))
    private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
        var player = (ServerPlayerEntity)(Object)this;

        if (source.getAttacker() != null) {
            var attacker = source.getAttacker();
            player.server_stats$incrementStat(Stats.getKilledByEntityStat(attacker), 1);
        }

        player.server_stats$incrementStat(Stats.DEATHS, 1);
    }
}
