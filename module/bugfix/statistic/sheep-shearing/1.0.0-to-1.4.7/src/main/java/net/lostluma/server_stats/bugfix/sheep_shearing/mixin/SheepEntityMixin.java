package net.lostluma.server_stats.bugfix.sheep_shearing.mixin;

import net.minecraft.entity.living.mob.passive.animal.SheepEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
	@Inject(
		method = "interact",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;damageAndBreak(ILnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void interact(PlayerEntity player, CallbackInfoReturnable<Boolean> callbackInfo) {
		player.incrementStat(Stats.ITEMS_USED[player.inventory.getMainHandStack().getItem().id]);
	}
}
