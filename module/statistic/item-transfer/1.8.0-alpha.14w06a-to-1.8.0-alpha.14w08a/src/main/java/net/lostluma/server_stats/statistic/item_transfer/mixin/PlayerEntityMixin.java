package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	protected abstract boolean isDead();

	/**
	 * Record the per-item drop statistic from player thrown items.
	 */
	@Inject(
		method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V")
	)
	private void dropItem(ItemStack itemStack, boolean velocityFromPlayerDirection, boolean thrownByPlayer, CallbackInfoReturnable<ItemEntity> callbackInfo) {
		String identifier = Item.REGISTRY.getKey(itemStack.getItem()).replace(":", ".");
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "drop." + identifier);

		if (!this.isDead() && statistic.isPresent()) {
			PlayerEntity player = (PlayerEntity)(Object) this;
			player.increment(statistic.get(), itemStack.size);
		}
	}
}
