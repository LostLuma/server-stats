package net.lostluma.server_stats.datafix;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resource.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerPlayerStatFix {
    private static final Logger LOGGER = LogManager.getLogger("server_stats");

    // Folder with backup of each stat file from previous versions in case something goes amiss.
    private static final Path BACKUPS = QuiltLoader.getCacheDir().resolve("server_stats");

    private static final Pattern UPGRADEABLE = Pattern.compile("^(?<type>stat.(?:breakItem|craftItem|mineBlock|useItem).)(?<id>\\d+)$");

    public static JsonObject upgradePlayerStats(File original, JsonObject data) throws IOException {
        var copy = upgradeStats(data);

        if (copy == null) {
            return data;
        }

        Files.createDirectories(BACKUPS);
        Files.copy(original.toPath(), BACKUPS.resolve(original.getName()), StandardCopyOption.REPLACE_EXISTING);

        return copy;
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

    private static String formatKey(Matcher match, Identifier identifier) {
        return match.group("type") + identifier.getNamespace() + "." + identifier.getPath();
    }

    private static @Nullable String getUpgradedStatName(String previous) {
        var match = UPGRADEABLE.matcher(previous);

        if (!match.matches()) {
            return null;
        }

        if (previous.toLowerCase().contains("block")) {
            var block = Block.byId(Integer.parseInt(match.group("id")));

            if (block != null) {
                return formatKey(match, Block.REGISTRY.getKey(block));
            } else {
                LOGGER.warn("Unable to upgrade statistic " + previous + ". No matching block found.");
            }
        } else {
            var item = Item.byId(Integer.parseInt(match.group("id")));

            if (item != null) {
                return formatKey(match, Item.REGISTRY.getKey(item));
            } else {
                LOGGER.warn("Unable to upgrade statistic " + previous + ". No matching item found.");
            }
        }

        return null;
    }
}
