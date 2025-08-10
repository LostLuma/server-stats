package net.lostluma.server_stats.statistic.movement.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.statistic.movement.Constants;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	PlayerEntityMixin(World world) {
		super(world);
	}

	@WrapOperation(
		method = "tickNonRidingMovementRelatedStats",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD", target = "Lnet/minecraft/entity/living/player/PlayerEntity;onGround:Z"
			)
		)
	)
	private void tickNonRidingMovementRelatedStats(PlayerEntity instance, Stat stat, int amount, Operation<Void> original) {
		ServerStatistic replacement = this.getProperOrDefault();

		if (replacement == null) {
			original.call(instance, stat, amount);
		} else {
			instance.increment(replacement, amount);
		}
	}

	@Unique
	private ServerStatistic getProperOrDefault() {
		if (this.isSneaking()) {
			return Constants.CM_CROUCHED;
		} else if (Constants.CM_SPRINTED != null && this.getFlag(3)) {
			return Constants.CM_SPRINTED;
		} else {
			return null;
		}
	}
}
