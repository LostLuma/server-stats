package net.lostluma.server_stats.bugfix.movement.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.bugfix.movement.duck.MovingPlayer;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements MovingPlayer {
	public PlayerEntityMixin(World world) {
		super(world);
	}

	@Unique
	private double server_stats$x;

	@Unique
	private double server_stats$y;

	@Unique
	private double server_stats$z;

	@Shadow
	protected abstract void tickNonRidingMovementRelatedStats(double par1, double par2, double par3);

	/**
	 * Update movement statistics after receiving a movement packet.
	 */
	@Override
	public void server_stats$move() {
		// Don't increment stats with initial position change
		if (this.server_stats$x != 0) {
			this.tickNonRidingMovementRelatedStats(this.x - this.server_stats$x, this.y - this.server_stats$y, this.z - this.server_stats$z);
		}

		this.server_stats$x = this.x;
		this.server_stats$y = this.y;
		this.server_stats$z = this.z;
	}

	/**
	 * Disable the original code counting how far the player moved.
	 * It does not work properly, and only ever records movements on the y axis.
	 */
	@WrapOperation(
		method = "moveEntityWithVelocity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;tickNonRidingMovementRelatedStats(DDD)V"
		)
	)
	private void skipVanillaCall(PlayerEntity instance, double x, double y, double z, Operation<Void> original) {
	}
}
