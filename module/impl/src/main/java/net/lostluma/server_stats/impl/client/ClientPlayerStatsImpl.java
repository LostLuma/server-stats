package net.lostluma.server_stats.impl.client;

import net.lostluma.server_stats.api.v1.player.DisplayStats;
import net.lostluma.server_stats.api.v1.util.Result;
import net.lostluma.server_stats.impl.player.DisplayStatsImpl;
import net.lostluma.server_stats.impl.service.ClientNetworking;
import net.lostluma.server_stats.impl.util.ResultImpl;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ClientPlayerStatsImpl {
	private static boolean inWorld;

	private static final Map<String, Consumer<Result<DisplayStats, String>>> nameRequests = new HashMap<>();
	private static final Map<UUID, Consumer<Result<DisplayStats, String>>> identifierRequests = new HashMap<>();

	public static void setInWorld(boolean value) {
		inWorld = value;
	}

	public static void fetch(String name, Consumer<Result<DisplayStats, String>> handler) {
		if (inWorld) {
			nameRequests.put(name, handler);
			ClientNetworking.INSTANCE.fetch(name);
		} else {
			handler.accept(ResultImpl.error("Currently not in a world."));
		}
	}

	public static void fetch(UUID identifier, Consumer<Result<DisplayStats, String>> handler) {
		if (inWorld) {
			identifierRequests.put(identifier, handler);
			ClientNetworking.INSTANCE.fetch(identifier);
		} else {
			handler.accept(ResultImpl.error("Currently not in a world."));
		}
	}

	public static void onResponse(@Nullable String name, @Nullable UUID identifier, @Nullable Map<String, Long> values, @Nullable String error) {
		Result<DisplayStats, String> result;
		Consumer<Result<DisplayStats, String>> handler;

		if (values != null) {
			result = ResultImpl.ok(new DisplayStatsImpl(values));
		} else if (error != null) {
			result = ResultImpl.error(error);
		} else {
			throw new RuntimeException("unreachable");
		}

		handler = nameRequests.remove(name);

		if (handler != null) {
			handler.accept(result);
		}

		handler = identifierRequests.remove(identifier);

		if (handler != null) {
			handler.accept(result);
		}
	}
}
