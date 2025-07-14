package net.lostluma.server_stats.bugfix.downwards_climbing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	public PlayerEntityMixin(World world) {
		super(world);
	}

	@Shadow
	public abstract void incrementStat(Stat stat, int amount);

	@Unique
	private static final boolean COUNTS_DOUBLE = countsDouble();

	/**
	 * Remove the original logic to count centimeters climbed.
	 */
	@WrapOperation(
		method = "tickNonRidingMovementRelatedStats",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V")
	)
	private void tickNonRidingMovementRelatedStats(PlayerEntity instance, Stat stat, int amount, Operation<Void> original) {
		if (!Stats.CM_CLIMB.equals(stat)) {
			original.call(instance, stat, amount);
		}
	}

	/**
	 * Count centimeters climbed, while accounting for downward and later movement.
	 */
	@Inject(
		method = "tickNonRidingMovementRelatedStats",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/block/material/Material;)Z"
		)
	)
	private void tickNonRidingMovementRelatedStats(double x, double y, double z, CallbackInfo callbackInfo) {
		if (this.isClimbing()) {
			int cm = Math.round(MathHelper.sqrt(x * x + y * y + z * z) * 100.0F);

			// In versions above the first 1.7 snapshot we only need to increment
			// The statistic by half the value, otherwise we award 2x the amount.
			if (COUNTS_DOUBLE) {
				cm /= 2;
			}

			this.incrementStat(Stats.CM_CLIMB, cm);
		}
	}

	@Unique
	private static boolean countsDouble() {
		return Platform.getModVersion("minecraft").compareTo(Version.of("1.7.0-alpha.13.36.a")) >= 0;
	}
}
