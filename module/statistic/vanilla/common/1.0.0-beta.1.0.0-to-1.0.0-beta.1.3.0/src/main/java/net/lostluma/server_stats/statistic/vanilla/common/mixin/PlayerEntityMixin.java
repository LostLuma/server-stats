package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.statistic.vanilla.common.util.Position;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.passive.animal.PigEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Unique
	private Position position;

	@Unique
	private Position minecartStart;

	public PlayerEntityMixin(World world) {
		super(world);
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick0(CallbackInfo callbackInfo) {
		PlayerEntity self = (PlayerEntity)(Object) this;
		Position updated = new Position(self.x, self.y, self.z, self.onGround);

		if (this.position != null) {
			this.awardJumpStatistic(self, this.position, updated);
			this.awardDistanceStatistic(self, this.position, updated);
		}

		this.position = updated;
	}

	@Unique
	private void awardJumpStatistic(PlayerEntity player, Position a, Position b) {
		if (a.onGround && !b.onGround && a.y < b.y) {
			player.increment(Statistics.JUMPS);
		}
	}

	@Unique
	private void awardDistanceStatistic(PlayerEntity player, Position a, Position b) {
		int distance = a.distanceTo(b);
		ServerStatistic statistic = null;

		if (player.vehicle == null) {
			if (player.isSubmergedIn(Material.WATER)) {
				statistic = Statistics.CM_DOVE;
			} else if (player.inWater) {
				statistic = Statistics.CM_SWUM;
				distance = a.horizontalDistanceTo(b);
			} else if (player.isClimbing()) {
				statistic = Statistics.CM_CLIMB;
			} else if (player.onGround) {
				statistic = Statistics.CM_WALKED;
			} else {
				statistic = Statistics.CM_FLOWN;
			}
		} else {
			if (player.vehicle instanceof PigEntity) {
				statistic = Statistics.CM_PIG;
			} else if (player.vehicle instanceof BoatEntity) {
				statistic = Statistics.CM_SAILED;
			} else if (player.vehicle instanceof MinecartEntity) {
				statistic = Statistics.CM_MINECART;

				if (this.minecartStart == null) {
					this.minecartStart = a;
				} else if (this.minecartStart.distanceTo(b) >= 1000.0F) {
					player.unlock(Achievements.TRAVEL_KILOMETER_BY_MINECART);
				}
			}
		}

		if (statistic != null) {
			player.increment(statistic, distance);
		}
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick1(CallbackInfo callbackInfo) {
		((PlayerEntity)(Object) this).increment(Statistics.MINUTES_PLAYED);
	}

	@Inject(
		method = "damage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/LivingEntity;damage(Lnet/minecraft/entity/Entity;I)Z"
		)
	)
	private void damage(Entity source, int amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		((PlayerEntity)(Object) this).increment(Statistics.DAMAGE_TAKEN, amount);
	}

	@Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;Z)V", at = @At("HEAD"))
	private void dropItem(ItemStack stack, boolean dead, CallbackInfo callbackInfo) {
		if (this.isAlive() && stack != null) {
			((PlayerEntity)(Object) this).increment(Statistics.DROPS, stack.size);
		}
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(Entity entity, CallbackInfo callbackInfo) {
		((PlayerEntity)(Object) this).increment(Statistics.DEATHS);
	}

	@WrapOperation(
		method = "attack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;attackEntity(Lnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void attack(ItemStack instance, LivingEntity target, Operation<Void> original) {
		((PlayerEntity)(Object) this).increment(Statistics.useItem(instance.itemId));
		original.call(instance, target);
	}
}
