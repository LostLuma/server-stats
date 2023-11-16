package net.lostluma.server_stats.stats;

import org.jetbrains.annotations.Nullable;

/*
 * A very cut-down version of the vanilla 1.7.10 Stat class.
 */
public class Stat {
	public final String key;

    public final @Nullable Integer vanillaId;

	public Stat(String key, @Nullable Integer vanillaId) {
		this.key = key;
        this.vanillaId = vanillaId;
	}

	public Stat register() {
		if (Stats.BY_KEY.containsKey(this.key)) {
			throw new RuntimeException("Duplicate stat id: \"" + ((Stat)Stats.BY_KEY.get(this.key)).key + "\" and \"" + this.key + ".");
		} else {
			Stats.ALL.add(this);
			Stats.BY_KEY.put(this.key, this);

            if (this.vanillaId != null) {
                Stats.BY_VANILLA_ID.put(this.vanillaId, this);
            }

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
