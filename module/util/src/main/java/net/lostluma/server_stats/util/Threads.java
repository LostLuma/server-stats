package net.lostluma.server_stats.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Threads {
	private static final AtomicInteger COUNT = new AtomicInteger(-1);

	public static void execute(Runnable runnable) {
		int value = COUNT.addAndGet(1);
		new Thread(runnable, "Server Stats/Task " + value).start();
	}
}
