package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	protected abstract boolean isDead();

	/**
	 * Record the per-item drop statistic from player thrown items.
	 */
	@Inject(
		method = "dropItem(Lnet/minecraft/item/ItemStack;Z)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V")
	)
	private void dropItem(ItemStack itemStack, boolean dead, CallbackInfo callbackInfo) {
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "drop." + itemStack.itemId);

		if (!this.isDead() && statistic.isPresent()) {
			PlayerEntity player = (PlayerEntity)(Object) this;
			player.increment(statistic.get(), itemStack.size);
		}
	}
}
