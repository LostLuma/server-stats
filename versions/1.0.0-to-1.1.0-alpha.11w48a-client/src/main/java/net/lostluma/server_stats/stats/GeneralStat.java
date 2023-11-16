package net.lostluma.server_stats.stats;

import org.jetbrains.annotations.Nullable;

public class GeneralStat extends Stat {
    public GeneralStat(String key, @Nullable Integer vanillaId) {
        super(key, vanillaId);
    }

    @Override
    public Stat register() {
        super.register();
        Stats.GENERAL.add(this);
        return this;
    }
}
