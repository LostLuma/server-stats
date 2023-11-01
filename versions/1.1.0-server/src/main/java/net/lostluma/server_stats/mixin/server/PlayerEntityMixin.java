package net.lostluma.server_stats.mixin.server;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stat;
import net.lostluma.server_stats.utils.StatsPlayer;
import net.minecraft.entity.living.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements StatsPlayer {
    @Unique
    private ServerPlayerStats server_stats$serverPlayerStats = null;

    @Override
    public void server_stats$incrementStat(Stat stat, int amount) {
        var stats = this.getStats();
        var player = (PlayerEntity)(Object)this;

        if (stats != null) {
            stats.increment(player, stat, amount);
        }
    }

    @Override
    public void server_stats$saveStats() {
        var stats = this.getStats();

        if (stats != null) {
            stats.save();
        }
    }

    private @Nullable ServerPlayerStats getStats() {
        var player = (PlayerEntity)(Object)this;

        if (!player.world.isMultiplayer) {
            return null;
        }

        if (this.server_stats$serverPlayerStats == null) {
            this.server_stats$serverPlayerStats = new ServerPlayerStats(player);
        }

        return this.server_stats$serverPlayerStats;
    }
}
