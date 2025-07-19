package net.lostluma.server_stats.impl.server;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

public class StatEventHandlerProxy implements StatEventHandler {
	// The weak reference will expire after player logs out
	private WeakReference<@Nullable StatEventHandler> parent;

	public StatEventHandlerProxy() {
		this.parent = new WeakReference<>(null);
	}

	public void setParent(StatEventHandler parent) {
		this.parent = new WeakReference<>(parent);
	}

	@Override
	public void server_stats$reset(ServerStatistic stat) {
		StatEventHandler parent = this.parent.get();

		if (parent != null) {
			parent.server_stats$reset(stat);
		}
	}

	@Override
	public void server_stats$push(ServerStatistic stat, long value) {
		StatEventHandler parent = this.parent.get();

		if (parent != null) {
			parent.server_stats$push(stat, value);
		}
	}
}
