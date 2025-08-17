package net.lostluma.server_stats.bugfix.result_forging.duck;

public interface DuckInventorySlot {
	default void server_stats$markResultSlot() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
