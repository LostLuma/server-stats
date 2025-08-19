package net.lostluma.server_stats.statistic.sheep_shearing.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.Constants;
import net.minecraft.entity.living.mob.passive.animal.SheepEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
	@Unique
	private static final ServerStatistic SHEEP_SHEARED = ServerStatistic.of(Constants.MOD_ID, "sheep_sheared").build();

	@Inject(
		method = "canInteract",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Random;nextInt(I)I"
		)
	)
	private void interact(PlayerEntity player, CallbackInfoReturnable<Boolean> callbackInfo) {
		player.increment(SHEEP_SHEARED);
	}
}
