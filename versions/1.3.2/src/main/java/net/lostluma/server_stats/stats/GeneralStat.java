package net.lostluma.server_stats.stats;

public class GeneralStat extends Stat {
	public GeneralStat(String key, String name) {
		super(key, name);
	}

	@Override
	public Stat register() {
		super.register();
		Stats.GENERAL.add(this);
		return this;
	}
}
