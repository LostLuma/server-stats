package net.lostluma.server_stats.bugfix.combat.mixin;

import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
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
	private static void register(int id, String key, Class<? extends Entity> type, String name, CallbackInfo ci) {
		if (LivingEntity.class.isAssignableFrom(type)) {
			TranslatableText text = new TranslatableText("entity." + key + ".name");

			new Stat("stat.killEntity." + name, new TranslatableText("stat.entityKill", text)).register();
			new Stat("stat.entityKilledBy." + name, new TranslatableText("stat.entityKilledBy", text)).register();
		}
	}
}
