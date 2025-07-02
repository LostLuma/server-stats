package net.lostluma.server_stats.bugfix.doubled_distance.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin  {
	@WrapWithCondition(
		method = "tickNonRidingMovmentRelatedStats",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/entity/living/player/PlayerEntity;onGround:Z"
			)
		)
	)
	private boolean tickNonRidingMovmentRelatedStats (PlayerEntity instance, Stat stat, int amount) {
		return !instance.isSneaking() && !instance.isSprinting();
	}
}
