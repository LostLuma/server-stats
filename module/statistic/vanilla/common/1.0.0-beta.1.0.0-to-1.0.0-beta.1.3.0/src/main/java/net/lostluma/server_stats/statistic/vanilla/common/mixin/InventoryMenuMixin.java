package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.lostluma.server_stats.statistic.vanilla.common.util.Context;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.menu.InventoryMenu;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(InventoryMenu.class)
public class InventoryMenuMixin {
	@Shadow
	public List<InventorySlot> slots;

	/**
	 * Store the clicked slot's item count as well as whether the player is already holding an item.
	 */
	@Inject(method = "onClickSlot", at = @At("HEAD"))
	private void onClickSlot(int index, int button, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("context") LocalRef<Context> reference) {
		InventorySlot slot;

		try {
			slot = this.slots.get(index);
		} catch (IndexOutOfBoundsException ignored) {
			return;
		}

		if (slot == null || slot.getClass() == InventorySlot.class) {
			return;
		}

		ItemStack stack = slot.getStack();

		if (stack != null) {
			reference.set(new Context(stack, player.inventory.getCursorStack() != null));
		}
	}

	/**
	 * Update the player's statistics with the amount crafted when switching the output and cursor item stacks.
	 */
	@Inject(
		method = "onClickSlot",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerInventory;setCursorStack(Lnet/minecraft/item/ItemStack;)V",
			ordinal = 4
		)
	)
	private void onSwitchStack(int index, int button, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("context") LocalRef<Context> reference) {
		Context context = reference.get();

		if (context != null) {
			this.awardStatistics(player, context.menuStack, context.size);
		}
	}

	/**
	 * Update the player's statistics with the amount created when taking a stack out of a crafting / furnace output slot.
	 */
	@Inject(
		method = "onClickSlot",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/inventory/slot/InventorySlot;onStackRemovedByPlayer()V"
		)
	)
	private void onStackRemovedByPlayer(int index, int button, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo, @Share("context") LocalRef<Context> reference) {
		Context context = reference.get();

		if (context == null) {
			return;
		}

		int difference;

		if (!context.holdingItem) {
			// Initial click on the crafting output
			// The taken stack is unmodified, unable to diff
			difference = context.size;
		} else {
			// Subsequent click on the crafting output
			// Calculate the difference from before -> after
			difference = context.size - context.menuStack.size;
		}

		this.awardStatistics(player, context.menuStack, difference);
	}

	@Unique
	private void awardStatistics(PlayerEntity player, ItemStack stack, int amount) {
		int itemId = stack.itemId;

		// Cake is added in Beta 1.2, not Beta 1.0
		if (Achievements.CRAFT_CAKE != null && itemId == 354) {
			player.unlock(Achievements.CRAFT_CAKE);
		}

		if (itemId == Item.BREAD.id) {
			player.unlock(Achievements.CRAFT_BREAD);
		}

		if (itemId == Block.FURNACE.id) {
			player.unlock(Achievements.CRAFT_FURNACE);
		}

		if (itemId == Item.IRON_INGOT.id) {
			player.unlock(Achievements.GET_IRON_INGOT);
		}

		if (itemId == Item.COOKED_FISH.id) {
			player.unlock(Achievements.COOK_FISH);
		}

		if (itemId == Block.CRAFTING_TABLE.id) {
			player.unlock(Achievements.CRAFT_CRAFTING_TABLE);
		}

		if (stack.getItem() instanceof HoeItem) {
			player.unlock(Achievements.CRAFT_WOODEN_HOE);
		}

		if (stack.getItem() instanceof SwordItem) {
			player.unlock(Achievements.CRAFT_SWORD);
		}

		if (stack.getItem() instanceof PickaxeItem) {
			player.unlock(Achievements.CRAFT_PICKAXE);

			if (itemId != Item.WOODEN_PICKAXE.id) {
				player.unlock(Achievements.CRAFT_BETTER_PICKAXE);
			}
		}

		player.increment(Statistics.craftItem(itemId), amount);
	}
}
