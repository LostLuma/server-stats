package net.lostluma.server_stats.bugfix.combat.mixin;

import net.minecraft.entity.Entities;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stats.class)
public abstract class StatsMixin {
	@Shadow
	public static Stat byKey(String key) {
		return null;
	}

	/**
	 * Return an existing entity kill stat, if it exists.
	 */
	@Inject(method = "createEntityKillStat", at = @At("HEAD"), cancellable = true)
	private static void createEntityKillStat(Entities.SpawnEggData spawnEggData, CallbackInfoReturnable<Stat> callbackInfo) {
		String key = spawnEggData.key;
		Stat found = byKey("stat.killEntity." + key);

		if (found != null) {
			callbackInfo.setReturnValue(found);
		}
	}

	/**
	 * Return an existing killed by entity stat, if it exists.
	 */
	@Inject(method = "createKilledByEntityStat", at = @At("HEAD"), cancellable = true)
	private static void createKilledByEntityStat(Entities.SpawnEggData spawnEggData, CallbackInfoReturnable<Stat> callbackInfo) {
		String key = spawnEggData.key;
		Stat found = byKey("stat.entityKilledBy." + key);

		if (found != null) {
			callbackInfo.setReturnValue(found);
		}
	}
}
