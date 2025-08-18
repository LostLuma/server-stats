package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.statistic.vanilla.client.util.Position;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {
	@Unique
	private Position position;

	@Unique
	private Position minecartStart;

	public PlayerEntityMixin(World world) {
		super(world);
	}

	@Inject(method = "tickAI", at = @At("RETURN"))
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

		player.increment(statistic, distance);
	}

	@WrapOperation(
		method = "damage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;damageAndBreak(I)V"
		)
	)
	private void damage(ItemStack instance, int amount, Operation<Void> original) {
		original.call(instance, amount);

		if (instance.size == 0) {
			((PlayerEntity)(Object) this).increment(Statistics.breakItem(instance.itemId));
		}
	}

	@Inject(method = "tickAI", at = @At("RETURN"))
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
}
