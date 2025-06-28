package net.lostluma.server_stats.bugfix.adventuring_time_persistence.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.stat.ServerPlayerStats;
import net.minecraft.stat.StatCounter;
import net.minecraft.stat.StatProgress;
import net.minecraft.util.ForwardingJsonSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerStats.class)
public class ServerPlayerStatsMixin {
	/**
	 * Ensure already-explored biomes are added to players' in-memory progress store.
	 */
	@WrapOperation(
		method = "deserialize",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/stat/StatCounter;setProgress(Lnet/minecraft/stat/StatProgress;)V"
		)
	)
	@SuppressWarnings("unchecked")
	private void deserialize(StatCounter instance, StatProgress progress, Operation<Void> original, @Local(ordinal = 1) JsonObject entry) {
		if (progress instanceof ForwardingJsonSet) {
			for (JsonElement element : entry.getAsJsonArray("progress")) {
				((ForwardingJsonSet) progress).add(element.getAsString());
			}
		}

		original.call(instance, progress);
	}
}
