package net.lostluma.server_stats.common.stat;

import org.jetbrains.annotations.Nullable;

public class ServerStat {
	public final String key;
	public final @Nullable Integer vanillaId;

	public ServerStat(String key, @Nullable Integer vanillaId) {
		this.key = key;
		this.vanillaId = vanillaId;
	}

	public int hashCode() {
		return this.key.hashCode();
	}

	public ServerStat register() {
		if (ServerStats.BY_KEY.containsKey(this.key)) {
			ServerStat other = ServerStats.byKey(this.key);
			throw new RuntimeException("Duplicate stat id: \"" + other.key + "\" and \"" + this.key + ".");
		} else {
			ServerStats.BY_KEY.put(this.key, this);

			if (this.vanillaId != null) {
				ServerStats.BY_VANILLA_ID.put(this.vanillaId, this);
			}

			return this;
		}
	}
}
