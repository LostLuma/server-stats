package net.lostluma.server_stats.statistic.vanilla.server.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
	@Shadow
	public PlayerEntity player;

	/**
	 * Store the block ID of the block being mined.
	 */
	@WrapOperation(
		method = "tryMineBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getBlock(III)I"
		)
	)
	private int tryMineBlock(World instance, int x, int y, int z, Operation<Integer> original, @Share("block") LocalIntRef ref) {
		int value = original.call(instance, x, y, z);
		ref.set(value);
		return value;
	}

	/**
	 * Increment the tool usage statistic if the item stack took damage.
	 */
	@WrapOperation(
		method = "tryMineBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;mineBlock(IIII)V"
		)
	)
	private void tryMineBlock(ItemStack instance, int block, int x, int y, int z, Operation<Void> original) {
		int damage = instance.metadata;
		original.call(instance, block ,x, y, z);

		if (damage != instance.metadata) {
			this.player.increment(Statistics.useItem(instance.itemId));
		}
	}

	/**
	 * Increment the block mined statistic should mining the block have been successful.
	 */
	@Inject(
		method = "tryMineBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/Block;afterMinedByPlayer(Lnet/minecraft/world/World;IIII)V"
		)
	)
	private void tryMineBlock(int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfo, @Share("block") LocalIntRef ref) {
		this.player.increment(Statistics.mineBlock(ref.get()));
	}
}
