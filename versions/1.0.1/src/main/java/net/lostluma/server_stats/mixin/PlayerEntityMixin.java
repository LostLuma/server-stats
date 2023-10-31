package net.lostluma.server_stats.mixin;

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
        var player = (PlayerEntity)(Object)this;
        this.server_stats$getStats().increment(player, stat, amount);
    }

    @Override
    public ServerPlayerStats server_stats$getStats() {
        if (this.server_stats$serverPlayerStats == null) {
            var player = (PlayerEntity)(Object)this;
            this.server_stats$serverPlayerStats = new ServerPlayerStats(player);
        }

        return this.server_stats$serverPlayerStats;
    }
}
