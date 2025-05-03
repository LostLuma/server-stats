package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.common.stat.ServerStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entities;

@Mixin(Entities.class)
public class EntitiesMixin {
	@Inject(method = "register", at = @At("TAIL"))
	private static void registerWithSpawnEgg(Class<?> type, String key, int id, CallbackInfo callbackInfo) {
		ServerStats.createEntityKillStat(key);
		ServerStats.createKilledByEntityStat(key);
	}
}
