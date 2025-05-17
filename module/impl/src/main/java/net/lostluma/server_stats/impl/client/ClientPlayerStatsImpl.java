package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.api.player.DisplayStats;
import net.lostluma.server_stats.impl.player.DisplayStatsImpl;
import net.lostluma.server_stats.impl.service.ClientNetworking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ClientPlayerStatsImpl {
	private static final Map<String, BiConsumer<@Nullable DisplayStats, @Nullable String>> nameRequests = new HashMap<>();
	private static final Map<UUID, BiConsumer<@Nullable DisplayStats, @Nullable String>> identifierRequests = new HashMap<>();

	public static void fetch(@NotNull String name, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler) {
		nameRequests.put(name, handler);
		ClientNetworking.INSTANCE.fetch(name);
	}

	public static void fetch(@NotNull UUID identifier, @NotNull BiConsumer<@Nullable DisplayStats, @Nullable String> handler) {
		identifierRequests.put(identifier, handler);
		ClientNetworking.INSTANCE.fetch(identifier);
	}

	public static void onResponse(@Nullable String name, @Nullable UUID identifier, @Nullable Map<String, Long> values, @Nullable String error) {
		DisplayStats stats = null;
		BiConsumer<@Nullable DisplayStats, @Nullable String> handler;

		if (values != null) {
			stats = new DisplayStatsImpl(values);
		}

		handler = nameRequests.remove(name);

		if (handler != null) {
			handler.accept(stats, error);
		}

		handler = identifierRequests.remove(identifier);

		if (handler != null) {
			handler.accept(stats, error);
		}
	}
}
