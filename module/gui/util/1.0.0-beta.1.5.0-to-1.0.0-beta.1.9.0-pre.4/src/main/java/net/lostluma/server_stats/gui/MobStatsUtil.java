package net.lostluma.server_stats.gui;

import net.minecraft.resource.language.I18n;
import org.jetbrains.annotations.Nullable;

public class MobStatsUtil {
	public static String getDisplayName(@Nullable String entityId) {
		entityId = entityId != null ? entityId : "generic";
		String key = "entity." + entityId + ".name";

		return translatableWithFallback(key, separateWith(' ', entityId));
	}

	public static String translatableWithFallback(String key, String fallback) {
		String translated = I18n.translate(key);

		if (I18n.translate(translated) != key) {
			return translated;
		} else {
			return fallback;
		}
	}

	public static String separateWith(char separator, String title) {
		StringBuilder spaceSeparated = new StringBuilder();

		for (char character : title.toCharArray()) {
			if (Character.isUpperCase(character) && spaceSeparated.length() != 0) {
				spaceSeparated.append(separator);
			}

			spaceSeparated.append(character);
		}

		return spaceSeparated.toString();
	}
}
