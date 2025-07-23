package net.lostluma.server_stats.event.client;

import net.lostluma.server_stats.event.Event;

public final class ClientWorldEvent {
	public static final Event<Event.Empty> JOIN = new Event<>();
	public static final Event<Event.Empty> LEFT = new Event<>();
}
