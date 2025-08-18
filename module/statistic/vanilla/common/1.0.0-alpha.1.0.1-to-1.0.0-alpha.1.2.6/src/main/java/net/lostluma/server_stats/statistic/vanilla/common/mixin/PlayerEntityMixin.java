package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	public PlayerEntityMixin(World world) {
		super(world);
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
