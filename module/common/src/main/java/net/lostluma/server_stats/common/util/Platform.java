package net.lostluma.server_stats.common.util;

import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.lostluma.server_stats.common.Constants;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Platform {
	private static final String BASE_DATA_PATH = "assets/" + Constants.MOD_ID + "/data";

	public static @NotNull Version getModVersion(String modId) {
		ModContainer container = getModContainer(modId);
		return Version.of(container.metadata().version().raw());
	}

	public static @NotNull String getHomepageUrl(String modId) {
		return getModContainer(modId).metadata().contactInfo().get("homepage");
	}

	public static @NotNull Environment getEnvironment() {
		if (MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT) {
			return Environment.CLIENT;
		} else {
			return Environment.SERVER;
		}
	}

	public static @NotNull Path getCacheDir() {
		Path path = QuiltLoader.getCacheDir().resolve(Constants.MOD_ID);

		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create Server Stats cache directory!", e);
		}

		return path;
	}

	public static <T> @NotNull T getJsonAsset(String name, Type type) throws IOException {
		ModContainer container = getModContainer(Constants.MOD_ID);
		Path path = container.getPath(BASE_DATA_PATH + "/" + name);
		return new Gson().fromJson(new String(Files.readAllBytes(path), StandardCharsets.UTF_8), type);
	}

	private static @NotNull ModContainer getModContainer(String modId) {
		// This is technically optional, but since the
		// mod IDs this is used on are static it doesn't matter
		return QuiltLoader.getModContainer(modId).get();
	}

	public enum Environment {
		CLIENT,
		SERVER;
	}
}
