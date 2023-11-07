package net.lostluma.server_stats.datafix;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ServerPlayerStatFix {
    private static final Logger LOGGER = LogManager.getLogger("server_stats");
    private static final Path BACKUPS = QuiltLoader.getCacheDir().resolve("server_stats");

    private static @Nullable Map<String, String> ID_MAP = null;
    private static final Pattern UPGRADEABLE = Pattern.compile("^(?<type>stat.(?:breakItem|craftItem|mineBlock|useItem).)(?<id>\\d+)$");

    public static void upgradePlayerStats(String worldDir) throws IOException {
        Path stats = Path.of(worldDir).resolve("stats");

        if (!Files.isDirectory(stats)) {
            return;
        }

        populateIdMap();

        try (Stream<Path> stream = Files.list(stats)) {
            Iterator<Path> files = stream.iterator();

            while (files.hasNext()) {
                upgradePlayerStats(files.next());
            }
        }

        ID_MAP = null; // Will never be used again during runtime of the program
    }

    private static void upgradePlayerStats(Path path) throws IOException {
        var data = JsonParser.parseString(Files.readString(path));

        if (!data.isJsonObject()) {
            throw new RuntimeException("Unable to upgrade stats file " + path.getFileName() + ".");
        }

        var copy = upgradeStats((JsonObject) data);

        if (copy == null) {
            return;
        }

        Files.createDirectories(BACKUPS);
        Files.move(path, BACKUPS.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        Files.writeString(path, copy.toString(), StandardCharsets.UTF_8);
    }

    private static @Nullable JsonObject upgradeStats(JsonObject data) throws IOException {
        var upgraded = false;
        var copy = new JsonObject();

        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            var replaced = getUpgradedStatName(key);

            if (replaced == null) {
                copy.add(key, value);
            } else {
                upgraded = true;
                copy.add(replaced, value);
            }
        }

        return upgraded ? copy : null;
    }

    private static @Nullable String getUpgradedStatName(String previous) {
        var match = UPGRADEABLE.matcher(previous);

        if (match.matches()) {
            var id = match.group("id");
            var type = match.group("type");

            var resourceLocation = ID_MAP.get(id);

            if (resourceLocation != null) {
                return type + "minecraft." + resourceLocation;
            } else {
                LOGGER.warn("Unable to upgrade statistic " + previous + ". ID is not known.");
            }
        }

        return null;
    }

    private static void populateIdMap() throws IOException {
        var modId = "server_stats_mixins";

        var container = QuiltLoader.getModContainer(modId).orElseThrow();
        var path = container.getPath("assets/" + modId + "/id_map.json");

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        ID_MAP = new Gson().fromJson(Files.readString(path, StandardCharsets.UTF_8), type);
    }
}
