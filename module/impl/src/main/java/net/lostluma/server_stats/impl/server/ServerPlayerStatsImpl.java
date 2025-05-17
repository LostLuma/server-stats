package net.lostluma.server_stats.impl.server;

import net.lostluma.server_stats.api.player.MutableStats;
import net.lostluma.server_stats.impl.error.NoContextAvailable;
import net.lostluma.server_stats.util.Mojang;
import net.lostluma.server_stats.util.Threads;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ServerPlayerStatsImpl {
	// TODO: Skip username / identifier lookup if the player is online
	public static void get(@NotNull String name, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		Threads.execute(() -> {
			UUID identifier;

			try {
				identifier = Mojang.fetchUuid(name);
			} catch (IOException e) {
				handler.accept(null, e.toString());
				return;
			}

			get(name, identifier, handler);
		});
	}

	public static void get(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		Threads.execute(() -> {
			String name;

			try {
				name = Mojang.fetchName(identifier);
			} catch (IOException e) {
				handler.accept(null, e.toString());
				return;
			}

			get(name, identifier, handler);
		});
	}

	private static void get(@NotNull String name, @NotNull UUID identifier, @NotNull BiConsumer<@Nullable MutableStats, @Nullable String> handler) {
		PlayerStatsCache cache;

		try {
			cache = PlayerStatsCache.getInstance();
		} catch (NoContextAvailable e) {
			handler.accept(null, e.toString());
			return;
		}

		handler.accept(cache.get(name, identifier), null);
	}
}
