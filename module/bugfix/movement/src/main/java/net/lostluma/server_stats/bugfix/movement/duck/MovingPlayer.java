package net.lostluma.server_stats.bugfix.movement.duck;

public interface MovingPlayer {
    default void server_stats$move() {
        throw new RuntimeException("Interface implementation missing!");
    }
}
