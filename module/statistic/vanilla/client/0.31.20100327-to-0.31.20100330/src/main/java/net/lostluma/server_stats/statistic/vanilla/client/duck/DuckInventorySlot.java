package net.lostluma.server_stats.statistic.vanilla.client.duck;

public interface DuckInventorySlot {
	default boolean server_stats$isResultSlot() {
		throw new RuntimeException("Interface implementation missing!");
	}

	default void server_stats$markResultSlot() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
