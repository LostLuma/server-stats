package net.lostluma.server_stats.bugfix.overkill.mixin;

import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.util.platform.Platform;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.resource.manager.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Mixin(value = TranslationStorage.class, priority = 2000)
public class LanguageManagerMixin {
	@Shadow
	Map<String, String> translations;

	/**
	 * Replace the Overkill achievement description with a corrected version.
	 */
	@Inject(method = "load(Lnet/minecraft/client/resource/manager/ResourceManager;Ljava/util/List;)V", at = @At("RETURN"))
	private void load(ResourceManager resourceManager, List<String> languageCodes, CallbackInfo callbackInfo) throws IOException {
		for (String language : languageCodes) {
			String key = language.toLowerCase(Locale.ROOT);

			Type type = new TypeToken<Map<String, String>>() {}.getType();
			Map<String, String> data = Platform.getJsonAsset("overkill.json", type);

			this.translations.put("achievement.overkill.desc", data.containsKey(key) ? data.get(key) : data.get("en_us"));
		}
	}
}
