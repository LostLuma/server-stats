package net.lostluma.server_stats.event.common;

import net.lostluma.server_stats.event.Event;

import java.nio.file.Path;

public final class ServerWorldEvent {
	private final Path path;

	public static final Event<ServerWorldEvent> LOAD = new Event<>();
	public static final Event<ServerWorldEvent> STOP = new Event<>();

	public ServerWorldEvent(Path path) {
		this.path = path;
	}

	/**
	 * @return The world folder's root path.
	 */
	public Path path() {
		return this.path;
	}
}
