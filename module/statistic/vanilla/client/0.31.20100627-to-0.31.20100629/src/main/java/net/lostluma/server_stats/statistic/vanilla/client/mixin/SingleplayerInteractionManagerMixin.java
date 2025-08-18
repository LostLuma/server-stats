package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.SingleplayerInteractionManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.unmapped.C_5664496;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SingleplayerInteractionManager.class)
public class SingleplayerInteractionManagerMixin extends ClientPlayerInteractionManager {
	public SingleplayerInteractionManagerMixin(C_5664496 minecraft) {
		super(minecraft);
	}

	/**
	 * Store the block ID of the block being mined.
	 */
	@WrapOperation(
		method = "finishMiningBlock",
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
		method = "finishMiningBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;mineBlock(Lnet/minecraft/item/ItemStack;)V"
		)
	)
	private void tryMineBlock(Item instance, ItemStack stack, Operation<Void> original) {
		int damage = stack.metadata;
		original.call(instance, stack);

		if (damage != stack.metadata) {
			this.minecraft.f_6058446.increment(Statistics.useItem(stack.itemId));

			if (stack.size == 0) {
				this.minecraft.f_6058446.increment(Statistics.breakItem(stack.itemId));
			}
		}
	}

	/**
	 * Increment the block mined statistic should mining the block have been successful.
	 */
	@Inject(
		method = "finishMiningBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/Block;dropItems(Lnet/minecraft/world/World;IIII)V"
		)
	)
	private void tryMineBlock(int i, int j, int k, CallbackInfoReturnable<Boolean> callbackInfo, @Share("block") LocalIntRef ref) {
		this.minecraft.f_6058446.increment(Statistics.mineBlock(ref.get()));
	}
}
