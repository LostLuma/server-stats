package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	/**
	 * Record the per-item drop statistic from player thrown items.
	 */
	@Inject(
		method = "dropItem(Lnet/minecraft/item/ItemStack;Z)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ItemEntity;<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"
		)
	)
	private void dropItem(ItemStack itemStack, boolean dead, CallbackInfo callbackInfo) {
		PlayerEntity player = (PlayerEntity)(Object) this;
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "drop." + itemStack.itemId);

		if (player.isAlive() && statistic.isPresent()) {
			player.increment(statistic.get(), itemStack.size);
		}
	}
}
