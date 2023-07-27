package net.lostluma.server_stats.stats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.entity.living.player.PlayerEntity;

@SuppressWarnings("squid:S2629") // Unconditional method call in log call
public class ServerPlayerStats {
    private final String name;
    protected final Map<Stat, Integer> counters;

    // Use Minecraft logger as it is already properly set up
    private static final Logger LOGGER = Logger.getLogger("Minecraft");
    private static final Path STATS = Path.of("world").resolve("stats");

    public ServerPlayerStats(PlayerEntity player) {
        this.name = player.getSourceName();
        this.counters = new ConcurrentHashMap<>();

        this.load();
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

    public void load() {
        Path path = STATS.resolve(this.name + ".json");

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
        Path path = STATS.resolve(this.name + ".json");

        try {
            Files.createDirectories(STATS);

            Path temp = Files.createTempFile(STATS, this.name, ".json");
            Files.writeString(temp, this.serialize(), StandardCharsets.UTF_8);

            Files.move(temp, path, StandardCopyOption.ATOMIC_MOVE); // Prevent issues on server crash
        } catch (IOException e) {
            LOGGER.warning(String.format("Failed to write stats to %s! %s", path, e.toString()));
        }
    }

    public void deserialize() throws IOException {
        Path path = STATS.resolve(this.name + ".json");
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
}
