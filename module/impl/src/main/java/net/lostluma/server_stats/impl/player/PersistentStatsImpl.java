package net.lostluma.server_stats.impl.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.ext.common.StatEventHandler;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.lostluma.server_stats.impl.service.ChatBroadcast;
import net.lostluma.server_stats.impl.statistic.ServerStatisticImpl;
import net.lostluma.server_stats.util.FSUtil;
import net.lostluma.server_stats.util.Logging;
import net.lostluma.server_stats.util.platform.Platform;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentStatsImpl implements PersistentStats {
	private final String username;
	private final UUID identifier;

	private final StatEventHandler handler;
	private final Map<String, Long> values;

	private boolean active;
	private boolean modified;

	public PersistentStatsImpl(String username, UUID identifier, StatEventHandler handler) {
		this.username = username;
		this.identifier = identifier;

		this.handler = handler;
		this.values = new ConcurrentHashMap<>();

		this.active = true;
		this.modified = false;

		try {
			this.load();
		} catch (IOException | JsonParseException e) {
			Logging.getLogger().error("Unable to read statistics for {}", this.username, e);
		}
	}

	@Override
	public long get(ServerStatistic stat) {
		return this.values.getOrDefault(this.getKey(stat), 0L);
	}

	@Override
	public long reset(ServerStatistic stat) throws IllegalStateException {
		this.checkActive();
		String key = this.getKey(stat);

		this.handler.server_stats$reset(stat);

		if (!this.values.containsKey(key)) {
			return 0L;
		} else {
			this.modified = true;
			return this.values.remove(key);
		}
	}

	@Override
	public long increment(ServerStatistic stat, long amount) throws IllegalStateException {
		this.checkActive();

		long value;
		this.modified = true;

		synchronized (this) {
			value = this.get(stat);
			this.values.put(this.getKey(stat), value + amount);
		}

		if (this.isModded(stat)) {
			this.handler.server_stats$push(stat, amount);
		}

		if (value == 0 && stat instanceof ServerAchievement) {
			ServerAchievement achievement = (ServerAchievement) stat;
			// TODO: Replace with translated achievement name, once that's possible
			ChatBroadcast.INSTANCE.announce(
				"§c" + this.username + "§r has earned the achievement §a" + achievement.identifier() + "§r"
			);
		}

		return value;
	}

	public StatEventHandler handler() {
		return this.handler;
	}

	private boolean isModded(ServerStatistic stat) {
		return ((ServerStatisticImpl) stat).vanillaId() == -1;
	}

	private String getKey(ServerStatistic stat) {
		return ((ServerStatisticImpl) stat).key();
	}

	@Override
	public void server_stats$close() {
		this.active = false;
	}

	@Override
	public Map<String, Long> server_stats$values() {
		return this.values;
	}

	private void checkActive() {
		if (!this.active) {
			// Switched worlds or server is in process of shutting down right now.
			throw new IllegalStateException("Stats context no longer available.");
		}
	}

	private void load() throws IOException, JsonParseException {
		Path base = PlayerStatsCache.getInstance().getPath();

		// Convert old name-based files to uuid-based ones
		Path name = base.resolve(this.username + ".json");
		Path uuid = base.resolve(this.identifier + ".json");

		if (Files.exists(name)) {
			Files.move(name, uuid);
		}

		if (Files.exists(uuid)) {
			this.deserialize(uuid);
		}
	}

	@Override
	public void server_stats$save() {
		if (!this.modified) {
			return;
		}

		this.modified = false;

		Path temp = Platform.getCacheDir();
		Path path = PlayerStatsCache.getInstance().getPath().resolve(this.identifier + ".json");

		try {
			// Create temporary file and move it to prevent
			// Corrupting statistics files on server crash.
			Path file = Files.createTempFile(temp, this.identifier.toString(), ".json", this.getDefaultFileAttributes());
			Files.write(file, this.serialize().getBytes(StandardCharsets.UTF_8));

			FSUtil.move(file, path);
		} catch (IOException e) {
			Logging.getLogger().error("Failed to write stats for {}!", this.username, e);
		}
	}

	public void deserialize(Path path) throws IOException {
		@SuppressWarnings("deprecation")
		JsonElement root = new JsonParser().parse(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));

		if (!root.isJsonObject()) {
			return;
		}

		JsonObject data = root.getAsJsonObject();

		for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
			String key = entry.getKey();
			JsonElement value = entry.getValue();

			if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
				this.values.put(key, value.getAsLong());
			} else {
				Logging.getLogger().warn("Failed to read stat {} in {}, ignoring!.", entry.getKey(), path);
			}
		}
	}

	private String serialize() {
		JsonObject result = new JsonObject();

		for (Map.Entry<String, Long> counter : this.values.entrySet()) {
			result.addProperty(counter.getKey(), counter.getValue());
		}

		return result.toString();
	}

	public String server_stats$serialize(boolean large) {
		JsonObject result = new JsonObject();

		for (Map.Entry<String, Long> counter : this.values.entrySet()) {
			boolean isLarge = counter.getValue() > Integer.MAX_VALUE;

			if ((large && isLarge) || (!large && !isLarge)) {
				result.addProperty(counter.getKey(), counter.getValue());
			}
		}

		return result.toString();
	}

	private FileAttribute<?>[] getDefaultFileAttributes() {
		if (!System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux")) {
			return new FileAttribute[0];
		}

		// Allow all users to read files we write to disk
		return new FileAttribute[]{ PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-r--r--")) };
	}
}
