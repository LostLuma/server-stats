package net.lostluma.server_stats.bugfix.the_end.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Swap constants to allow The End? to work, and We Need To Go Deeper to not erroneously trigger:
 * <br>
 * {@code - if (this.dimensionId == 1 && dimensionId == 0)}
 * <br>
 * {@code + if (this.dimensionId == 0 && dimensionId == 1)}
 * <br>
 * Note: Dimension ID 0 is the overworld, 1 is the end dimension.
 */
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	/**
	 * Fix the left-hand side of the statement.
	 */
	@Definition(id = "dimensionId", field = "Lnet/minecraft/server/entity/living/player/ServerPlayerEntity;dimensionId:I")
	@Expression("this.dimensionId == 1")
	@WrapOperation(method = "teleportToDimension",	at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
	private boolean teleportToDimension0(int dimensionId, int test, Operation<Boolean> original) {
		return dimensionId == 0;
	}

	/**
	 * Fix the right-hand side of the statement.
	 */
	@Definition(id = "dimensionId", local = @Local(type = int.class, argsOnly = true))
	@Expression("dimensionId == 0")
	@WrapOperation(method = "teleportToDimension",	at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean teleportToDimension1(int dimensionId, int test, Operation<Boolean> original) {
		return dimensionId == 1;
	}
}
