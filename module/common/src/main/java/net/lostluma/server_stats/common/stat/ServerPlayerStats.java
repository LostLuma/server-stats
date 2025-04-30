package net.lostluma.server_stats.common.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.lostluma.server_stats.common.duck.DuckPlayer;
import net.lostluma.server_stats.common.util.FSUtil;
import net.lostluma.server_stats.common.util.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ServerPlayerStats {
	private final @NotNull DuckPlayer player;
	protected final @NotNull Map<String, Long> counters;

	private static @Nullable Path STATS = null;

	// Use Minecraft logger as it is already properly set up
	private static final Logger LOGGER = Logger.getLogger("Minecraft");

	public ServerPlayerStats(@NotNull DuckPlayer player) {
		this.player = player;
		this.counters = new ConcurrentHashMap<>();

		this.load();
	}

	public static void setWorldDirectory(String worldDirName) {
		STATS = Paths.get(worldDirName).resolve("stats");
	}

	public long increment(@NotNull ServerStat stat, int amount) {
		long value = this.get(stat);
		this.counters.put(stat.key, value + amount);

		// Handle namespaced stat push to the client
		// Minecraft handles vanilla-registered ones
		if (stat.vanillaId == null) {
			this.player.server_stats$push(stat, amount);
		}

		return value;
	}

	public long get(ServerStat stat) {
		return this.counters.getOrDefault(stat.key, 0L);
	}

	public Map<String, Long> getRawStats() {
		return this.counters;
	}

	public void load() {
		try {
			this.load0();
		} catch (IOException | JsonParseException e) {
			LOGGER.severe("Unable to read statistics file for " + this.player.server_stats$name() + "!");
		}
	}

	private void load0() throws IOException, JsonParseException {
		Path base = this.getStatsDirectory();

		// Convert old name-based files to uuid-based ones
		Path name = base.resolve(this.player.server_stats$name() + ".json");
		Path uuid = base.resolve(this.player.server_stats$identifier() + ".json");

		if (Files.exists(name)) {
			Files.move(name, uuid);
		}

		if (Files.exists(uuid)) {
			this.deserialize(uuid);
		}
	}

	public void save() {
		Path temp = Platform.getCacheDir();
		Path path = this.getStatsDirectory().resolve(this.player.server_stats$identifier() + ".json");

		try {
			Files.createDirectories(path.getParent());

			// Create temporary file and move it to prevent
			// Corrupting statistics files on server crash.
			Path file = Files.createTempFile(temp, this.player.server_stats$identifier(), ".json", this.getDefaultFileAttributes());
			Files.write(file, this.serialize().getBytes(StandardCharsets.UTF_8));

			FSUtil.move(file, path);
		} catch (IOException e) {
			LOGGER.severe(String.format("Failed to write stats for %s! %s", this.player.server_stats$name(), e));
		}
	}

	public void deserialize(Path path) throws IOException {
		JsonElement root = JsonParser.parseString(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));

		if (!root.isJsonObject()) {
			return;
		}

		JsonObject data = root.getAsJsonObject();

		for (Entry<String, JsonElement> entry : data.entrySet()) {
			String key = entry.getKey();
			JsonElement value = entry.getValue();

			if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
				this.counters.put(key, value.getAsLong());
			} else {
				LOGGER.warning(String.format("Failed to read stat %s in %s, ignoring!.", entry.getKey(), path));
			}
		}
	}

	public String serialize() {
		JsonObject result = new JsonObject();

		for (Entry<String, Long> counter : this.counters.entrySet()) {
			result.addProperty(counter.getKey(), counter.getValue());
		}

		return result.toString();
	}

	/**
	 * Serialize either only small, or large values.
	 * This is needed to ensure older Server Stats clients can receive data.
	 */
	public String serialize(boolean large) {
		JsonObject result = new JsonObject();

		for (Entry<String, Long> counter : this.counters.entrySet()) {
			boolean isLarge = counter.getValue() > Integer.MAX_VALUE;

			if ((large && isLarge) || (!large && !isLarge)) {
				result.addProperty(counter.getKey(), counter.getValue());
			}
		}

		return result.toString();
	}

	private @NotNull Path getStatsDirectory() {
		Objects.requireNonNull(STATS, "Stats directory unset.");
		return STATS;
	}

	private @NotNull FileAttribute<?>[] getDefaultFileAttributes() {
		if (!System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux")) {
			return new FileAttribute[0];
		}

		// Allow all users to read files we write to disk
		return new FileAttribute[]{ PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-r--r--")) };
	}
}
