package net.lostluma.server_stats.statistic.combat.mixin.common;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.minecraft.entity.Entities;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entities.class)
public class EntitiesMixin {
	@Inject(method = "register", at = @At("TAIL"))
	private static void registerWithSpawnEgg(Class<?> type, String key, int id, boolean hasSpawnEgg, CallbackInfo callbackInfo) {
		if (LivingEntity.class.isAssignableFrom(type)) {
			ServerStatistic.of("minecraft", "killEntity." + key).build();
			ServerStatistic.of("minecraft", "entityKilledBy." + key).build();
		}
	}
}
