package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.stats.Stats;
import net.lostluma.server_stats.types.StatsPlayer;
import net.minecraft.entity.living.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stat;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements StatsPlayer {
    @Unique
    private ServerPlayerStats server_stats$serverPlayerStats = null;

    private PlayerEntity getPlayer() {
        return (PlayerEntity) (Object) this;
    }

    @Override
    public void server_stats$incrementStat(Stat stat, int amount) {
        ServerPlayerStats stats = this.server_stats$getStats();

        if (stats != null) {
            stats.increment(this.getPlayer(), stat, amount);
        }
    }

    @Override
    public void server_stats$saveStats() {
        ServerPlayerStats stats = this.server_stats$getStats();

        if (stats != null) {
            stats.save();
        }
    }

    @Override
    public @Nullable ServerPlayerStats server_stats$getStats() {
        PlayerEntity player = this.getPlayer();

        if (player.world.isClient) {
            return null;
        }

        if (this.server_stats$serverPlayerStats == null) {
            this.server_stats$serverPlayerStats = new ServerPlayerStats(player);
        }

        return this.server_stats$serverPlayerStats;
    }

    @Inject(method = "onKill", at = @At("HEAD"))
    private void onKill(LivingEntity entity, CallbackInfo callbackInfo) {
        this.getPlayer().server_stats$incrementStat(Stats.getEntityKillStat(entity), 1);
    }
}
