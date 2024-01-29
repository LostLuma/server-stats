package net.lostluma.server_stats.stats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.entity.living.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("squid:S2629") // Unconditional method call in log call
public class ServerPlayerStats {
    private final String name;
    protected final Map<Stat, Integer> counters;

    private static @Nullable Path STATS = null;

    // Use Minecraft logger as it is already properly set up
    private static final Logger LOGGER = Logger.getLogger("Minecraft");

    private static final FileAttribute<?>[] DEFAULT_ATTRIBUTES = getDefaultFileAttributes();

    public ServerPlayerStats(PlayerEntity player) {
        this.name = player.getSourceName();
        this.counters = new ConcurrentHashMap<>();

        this.load();
    }

    public static void setWorldDirectory(String worldDirName) {
        STATS = Path.of(worldDirName).resolve("stats");
    }

    public void increment(PlayerEntity player, Stat stat, int amount) {
        if (stat != null) {
            this.set(player, stat, this.get(stat) + amount);
        }
    }

    public void set(PlayerEntity player, Stat stat, int value) {
        this.counters.put(stat, value);
    }

    public int get(Stat stat) {
        return this.counters.getOrDefault(stat, 0);
    }

    public Map<String, Integer> getRawStats() {
        Map<String, Integer> raw = new HashMap<>();

        for (Entry<Stat, Integer> counter : this.counters.entrySet()) {
            raw.put(counter.getKey().key, counter.getValue());
        }

        return raw;
    }

    public void load() {
        Path path = getStatsDirectory().resolve(this.name + ".json");

        if (Files.exists(path)) {
            try {
                this.deserialize();
            } catch (IOException e) {
                LOGGER.severe("Couldn't read statistics file " + path);
            } catch (JsonParseException e) {
                LOGGER.severe("Couldn't parse statistics file " + path);
            }
        }
    }

    public void save() {
        Path base = getStatsDirectory();
        Path path = base.resolve(this.name + ".json");

        try {
            Files.createDirectories(base);

            Path temp = Files.createTempFile(base, this.name, ".json", DEFAULT_ATTRIBUTES);
            Files.writeString(temp, this.serialize(), StandardCharsets.UTF_8);

            Files.move(temp, path, StandardCopyOption.ATOMIC_MOVE); // Prevent issues on server crash
        } catch (IOException e) {
            LOGGER.warning(String.format("Failed to write stats to %s! %s", path, e.toString()));
        }
    }

    public void deserialize() throws IOException {
        Path path = getStatsDirectory().resolve(this.name + ".json");
        JsonElement root = JsonParser.parseString(Files.readString(path, StandardCharsets.UTF_8));

        if (!root.isJsonObject()) {
            return;
        }

        JsonObject data = root.getAsJsonObject();

        for (Entry<String, JsonElement> entry : data.entrySet()) {
            JsonElement value = entry.getValue();
            Stat stat = Stats.byKey(entry.getKey());

            if (stat != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
                this.counters.put(stat, value.getAsInt());
            } else {
                LOGGER.warning(String.format("Failed to read stat %s in %s! It's either unknown or has invalid data.",
                        entry.getKey(), path));
            }
        }
    }

    public String serialize() {
        JsonObject result = new JsonObject();

        for (Entry<Stat, Integer> counter : this.counters.entrySet()) {
            result.addProperty(counter.getKey().key, counter.getValue());
        }

        return result.toString();
    }

    private static Path getStatsDirectory() {
        Objects.requireNonNull(STATS, "Stats directory unset.");
        return STATS;
    }

    private static FileAttribute<?>[] getDefaultFileAttributes() {
        if (!System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux")) {
            return new FileAttribute[0];
        }

        return new FileAttribute[]{PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-r--r--"))};
    }
}
