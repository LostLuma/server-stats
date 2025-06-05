package net.lostluma.server_stats.item_transfer.duck;

public interface DuckIdRegistry {
	default void server_stats$setIsItemRegistry() {
		throw new RuntimeException("Interface implementation missing!");
	}
}
