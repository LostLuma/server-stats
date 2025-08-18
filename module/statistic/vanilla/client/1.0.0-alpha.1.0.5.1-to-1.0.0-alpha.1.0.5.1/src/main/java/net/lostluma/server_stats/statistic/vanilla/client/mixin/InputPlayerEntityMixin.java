package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.statistic.vanilla.client.util.ItemStackUtil;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputPlayerEntity.class)
public class InputPlayerEntityMixin {
	@WrapOperation(
		method = "m_5027852",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;attackEntity(Lnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void attack(ItemStack instance, LivingEntity target, Operation<Void> original) {
		ItemStack copy = ItemStackUtil.copy(instance);
		original.call(instance, target);

		if (!ItemStackUtil.matches(copy, instance)) {
			((PlayerEntity)(Object) this).increment(Statistics.useItem(instance.itemId));
		}
	}
}
