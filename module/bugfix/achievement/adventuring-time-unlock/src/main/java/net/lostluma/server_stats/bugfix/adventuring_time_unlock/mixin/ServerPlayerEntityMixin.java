package net.lostluma.server_stats.bugfix.adventuring_time_unlock.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.util.ForwardingJsonSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	/**
	 * Allow the Adventuring Time biome check to run once the player has
	 * visited more biomes than there are in the explorable biomes requirement set.
	 * <br>
	 * By default, the count must be equal, which can never be the case
	 * as biomes that the achievement does not require are also added to the player's set.
	 */
	@Definition(id = "progress", local = @Local(type = ForwardingJsonSet.class))
	@Definition(id = "exploredSize", method = "Lnet/minecraft/util/ForwardingJsonSet;size()I")
	@Definition(id = "EXPLORABLE", field = "Lnet/minecraft/world/biome/Biome;EXPLORABLE:Ljava/util/Set;")
	@Definition(id = "explorableSize", method = "Ljava/util/Set;size()I")
	@Expression("progress.exploredSize() == EXPLORABLE.explorableSize()")
	@WrapOperation(method = "updateExploreredBiomes", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean updateExploredBiomes(int left, int right, Operation<Boolean> original) {
		return left >= right;
	}
}
