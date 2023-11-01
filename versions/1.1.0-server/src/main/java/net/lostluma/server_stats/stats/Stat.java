package net.lostluma.server_stats.stats;

/*
 * V very cut-down version of the vanilla 1.7.10 Stat class.
 */
public class Stat {
	public final String key;
	private final String name;
	public boolean local;

	public Stat(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public Stat register() {
		if (Stats.BY_KEY.containsKey(this.key)) {
			throw new RuntimeException("Duplicate stat id: \"" + ((Stat)Stats.BY_KEY.get(this.key)).name + "\" and \"" + this.name + "\" at id " + this.key);
		} else {
			Stats.ALL.add(this);
			Stats.BY_KEY.put(this.key, this);
			return this;
		}
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			Stat var2 = (Stat)object;
			return this.key.equals(var2.key);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.key.hashCode();
	}
}
