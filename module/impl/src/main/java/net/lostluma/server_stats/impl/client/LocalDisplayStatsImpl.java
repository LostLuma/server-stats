package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.player.DisplayStatsImpl;

import java.util.Map;
import java.util.UUID;

public class LocalDisplayStatsImpl extends DisplayStatsImpl implements Identifiable, StatEventHandler {
	private final Identifiable parent;

	public LocalDisplayStatsImpl(Map<String, Long> values, Identifiable parent) {
		super(values);
		this.parent = parent;
	}

	public void add(String key, long value) {
		this.values.put(key, this.values.getOrDefault(key, 0L) + value);
	}

	public void reset(String key) {
		this.values.remove(key);
	}

	public void persist(Map<String, Long> overrides, boolean clear) {
		if (clear) {
			this.values.clear();
		}

		for (Map.Entry<String, Long> entry : overrides.entrySet()) {
			String key = entry.getKey();
			this.values.put(key, entry.getValue() + this.values.getOrDefault(key, 0L));
		}
	}

	// Identifiable

	@Override
	public String server_stats$name() {
		return this.parent.server_stats$name();
	}

	@Override
	public UUID server_stats$identifier() {
		return this.parent.server_stats$identifier();
	}

	// StatEventHandler

	@Override
	public void server_stats$reset(ServerStatistic stat) {
		this.reset(this.getKey(stat));
	}

	@Override
	public void server_stats$push(ServerStatistic stat, long value) {
		this.add(this.getKey(stat), value);
	}
}
