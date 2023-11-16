package net.lostluma.server_stats.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.Constants;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class Serialization {
    private static final String BASE_PATH = "assets/" + Constants.MOD_ID + "/data";

    public static Map<String, Integer> getFromAssets(String name) throws IOException {
        var container = QuiltLoader.getModContainer(Constants.MOD_ID).orElseThrow();
        var path = container.getPath(BASE_PATH + "/" + name + ".json");

        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        return new Gson().fromJson(Files.readString(path, StandardCharsets.UTF_8), type);
    }
}