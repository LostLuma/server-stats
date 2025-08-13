package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.FishingBobberEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
	@Shadow
	public PlayerEntity player;

	@Inject(
		method = "retract",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;addEntity(Lnet/minecraft/entity/Entity;)Z"
		)
	)
	private void retract(CallbackInfoReturnable<Integer> callbackInfo) {
		this.player.increment(Statistics.CATCH_FISH);
	}
}
