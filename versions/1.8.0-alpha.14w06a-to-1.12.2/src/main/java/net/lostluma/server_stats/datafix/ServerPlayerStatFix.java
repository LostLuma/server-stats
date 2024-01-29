package net.lostluma.server_stats.datafix;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ServerPlayerStatFix {
    private static final Logger LOGGER = LogManager.getLogger("server_stats");
    private static final Path BACKUPS = QuiltLoader.getCacheDir().resolve("server_stats");

    private static @Nullable Map<String, String> ID_MAP = null;
    private static final Pattern UPGRADEABLE = Pattern.compile("^(?<type>stat.(?:breakItem|craftItem|mineBlock|useItem).)(?<id>\\d+)$");

    public static void upgradePlayerStats(String worldDir) throws IOException {
        Path stats = Paths.get(worldDir).resolve("stats");

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
        JsonElement data = JsonParser.parseString(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));

        if (!data.isJsonObject()) {
            throw new RuntimeException("Unable to upgrade stats file " + path.getFileName() + ".");
        }

        JsonObject copy = upgradeStats((JsonObject) data);

        if (copy == null) {
            return;
        }

        Files.createDirectories(BACKUPS);
        Files.move(path, BACKUPS.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        Files.write(path, copy.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static @Nullable JsonObject upgradeStats(JsonObject data) throws IOException {
        boolean upgraded = false;
        JsonObject copy = new JsonObject();

        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            String replaced = getUpgradedStatName(key);

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
        Matcher match = UPGRADEABLE.matcher(previous);

        if (match.matches()) {
            String id = match.group("id");
            String type = match.group("type");

            Object resourceLocation = ID_MAP.get(id);

            if (resourceLocation != null) {
                return type + "minecraft." + resourceLocation;
            } else {
                LOGGER.warn("Unable to upgrade statistic " + previous + ". ID is not known.");
            }
        }

        return null;
    }

    private static void populateIdMap() throws IOException {
        Optional<ModContainer> container = QuiltLoader.getModContainer(Constants.MOD_ID);

        if (!container.isPresent()) {
            throw new RuntimeException("Unable to get own mod container!");
        }

        Path path = container.get().getPath("assets/" + Constants.MOD_ID + "/data/id_map.json");

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        ID_MAP = new Gson().fromJson(new String(Files.readAllBytes(path), StandardCharsets.UTF_8), type);
    }
}
