package net.lostluma.server_stats.util.platform;

import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.lostluma.server_stats.util.Constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Platform {
	private static final FabricLoader loader = FabricLoader.getInstance();
	private static final String BASE_DATA_PATH = "assets/" + Constants.MOD_ID + "/data";

	public static Version getModVersion(String modId) {
		ModContainer container = getModContainer(modId);
		return Version.of(container.getMetadata().getVersion().toString());
	}

	public static String getHomepageUrl(String modId) {
		return getModContainer(modId).getMetadata().getContact().get("homepage").get();
	}

	public static Environment getEnvironment() {
		if (loader.getEnvironmentType() == EnvType.CLIENT) {
			return Environment.CLIENT;
		} else {
			return Environment.SERVER;
		}
	}

	public static Path getCacheDir() {
		Path path = loader.getGameDir();

		for (String name : new String[] { ".cache", Constants.MOD_ID }) {
			path = path.resolve(name);
		}

		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create Pumpkin Patch cache directory!", e);
		}

		return path;
	}

	public static <T> T getJsonAsset(String name, Type type) throws IOException {
		ModContainer container = getModContainer(Constants.MOD_ID);
		Path path = container.findPath(BASE_DATA_PATH + "/" + name).get();
		return new Gson().fromJson(new String(Files.readAllBytes(path), StandardCharsets.UTF_8), type);
	}

	private static ModContainer getModContainer(String modId) {
		// This is technically optional, but since the
		// mod IDs this is used on are static it doesn't matter
		return loader.getModContainer(modId).get();
	}

	public enum Environment {
		CLIENT,
		SERVER,
	}
}
