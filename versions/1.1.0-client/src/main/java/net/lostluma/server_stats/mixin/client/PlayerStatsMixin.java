package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.types.OverridableStats;
import net.lostluma.server_stats.utils.Serialization;
import net.minecraft.stat.PlayerStats;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin implements OverridableStats {
    @Shadow

    private Map<Stat, Integer> stats;

    private static final Pattern STAT_PATTERN = Pattern.compile("^(?<type>stat.(?:breakItem|craftItem|mineBlock|useItem).)(?<id>\\d+)$");

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo callbackInfo) {
        this.stats = Collections.synchronizedMap(this.stats);
    }

    @Override
    public void player_stats$override(Map<String, Integer> override) {
        try {
            this.player_stats$override0(override);
        } catch (IOException e) {
            System.out.println("Failed to override local stats " + e.getMessage());
        }
    }

    private void player_stats$override0(Map<String, Integer> override) throws IOException {
        // Reset all stats for the case where the world joined has some stats not set
        this.stats.keySet().removeAll(
            this.stats.keySet().stream().filter(integer -> !integer.isAchievement()).toList()
        );

        var ids = Serialization.getFromAssets("stat_ids");
        var prefixes = Serialization.getFromAssets("stat_id_prefixes");

        override.forEach((key, value) -> {
            Stat stat = null;
            var match = STAT_PATTERN.matcher(key);

            if (ids.containsKey(key)) {
                stat = Stats.byKey(ids.get(key));
            } else if (match.matches()) {
                var id = match.group("id");
                var type = match.group("type");

                if (prefixes.containsKey(type)) {
                    stat = Stats.byKey(Integer.parseInt(id) + prefixes.get(type));
                }
            }

            if (stat != null) {
                this.stats.put(stat, value);
            } else {
                System.out.println("No client-side stat found for key " + key);
            }
        });
    }
}