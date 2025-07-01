package net.lostluma.server_stats.bugfix.overkill.mixin;

import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.util.platform.Platform;
import net.minecraft.locale.LanguageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

@Mixin(value = LanguageManager.class, priority = 2000)
public class LanguageManagerMixin {
	@Shadow
	private Properties translations;

	/**
	 * Replace the Overkill achievement description with a corrected version.
	 */
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) throws IOException {
		String key = "en_us";

		Type type = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> data = Platform.getJsonAsset("overkill.json", type);

		this.translations.put("achievement.overkill.desc", data.containsKey(key) ? data.get(key) : data.get("en_us"));
	}
}
