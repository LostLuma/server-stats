package net.lostluma.server_stats.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Event<T> {
	private final List<Consumer<T>> listeners;

	public Event() {
		this.listeners = new ArrayList<>();
	}

	public void dispatch(T value) {
		this.listeners.forEach(x -> x.accept(value));
	}

	public void register(Consumer<T> listener) {
		this.listeners.add(listener);
	}

	public static final class Empty {
		private Empty() {}
	}

	public static final Empty EMPTY = new Empty();
}
