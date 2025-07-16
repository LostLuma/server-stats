package net.lostluma.server_stats.bugfix.combat.mixin;

import net.minecraft.entity.Entities;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.stat.Stat;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entities.class)
public class EntitiesMixin {
	/**
	 * Register combat stats for any living entity, regardless of it having a spawn egg.
	 */
	@Inject(method = "register", at = @At("RETURN"))
	private static void register(Class<?> type, String key, int id, CallbackInfo callbackInfo) {
		if (LivingEntity.class.isAssignableFrom(type)) {
			TranslatableText name = new TranslatableText("entity." + key + ".name");

			new Stat("stat.killEntity." + key, new TranslatableText("stat.entityKill", name)).register();
			new Stat("stat.entityKilledBy." + key, new TranslatableText("stat.entityKilledBy", name)).register();
		}
	}
}
