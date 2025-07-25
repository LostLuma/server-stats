package net.lostluma.server_stats.event.common;

import net.lostluma.server_stats.event.Event;

public final class GameEvent {
	/**
	 * Dispatched every game tick (20 times per second).
	 * <br>
	 * Note: This event may not dispatch client-side when not hosting a world.
	 */
	public static final Event<Event.Empty> TICK = new Event<>();
}
