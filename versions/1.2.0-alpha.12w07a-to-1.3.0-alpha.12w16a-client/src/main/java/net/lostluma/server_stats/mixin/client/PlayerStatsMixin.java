package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.types.OverridableStats;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.Stat;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Map;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin implements OverridableStats {
    @Shadow
    private Map<Stat, Integer> stats;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo callbackInfo) {
        this.stats = Collections.synchronizedMap(this.stats);
    }

    @Override
    public void player_stats$override(ServerPlayerStats override) {
        this.player_stats$doOverride(override.getRawStats());
    }

    @Override
    public void player_stats$override(Map<String, Integer> overrides) {
        this.player_stats$doOverride(overrides);
    }

    private void player_stats$doOverride(Map<String, Integer> overrides) {
        // Reset all stats for the case where the world joined has some stats not set
        this.stats.keySet().removeAll(
            this.stats.keySet().stream().filter(integer -> !integer.isAchievement()).toList()
        );

        overrides.forEach((key, value) -> {
            var stat = this.player_stats$getVanillaStat(key);

            if (stat != null) {
                this.stats.put(stat, value);
            } else {
                System.out.println("No client-side stat found for key " + key + ".");
            }
        });
    }

    private @Nullable Stat player_stats$getVanillaStat(String key) {
        var stat = net.lostluma.server_stats.stats.Stats.byKey(key);

        if (stat == null || stat.vanillaId == null) {
            return null;
        } else {
            return net.minecraft.stat.Stats.byKey(stat.vanillaId);
        }
    }
}
