package net.lostluma.server_stats.common.util;

import com.google.gson.Gson;
import net.lostluma.server_stats.common.Constants;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Platform {
	private static final String BASE_PATH = "assets/" + Constants.MOD_ID + "/data";

	public static @NotNull Path getCacheDir() {
		Path path = QuiltLoader.getCacheDir().resolve(Constants.MOD_ID);

		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create Server Stats cache directory!", e);
		}

		return path;
	}

	public static <T> T getJsonAsset(String name, Type type) throws IOException {
		Optional<ModContainer> container = QuiltLoader.getModContainer(Constants.MOD_ID);

		if (!container.isPresent()) {
			throw new RuntimeException("Unable to get own mod container!");
		}

		Path path = container.get().getPath(BASE_PATH + "/" + name);
		return new Gson().fromJson(new String(Files.readAllBytes(path), StandardCharsets.UTF_8), type);
	}
}
