package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.lostluma.server_stats.statistic.vanilla.client.util.Context;
import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin extends Screen {
	/**
	 * Store the clicked slot's item count as well as whether the player is already holding an item.
	 */
	@WrapOperation(
		method = "mouseClicked",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/inventory/slot/InventorySlot;inventory:Lnet/minecraft/inventory/Inventory;",
			ordinal = 0
		)
	)
	private Inventory onClickSlot(InventorySlot instance, Operation<Inventory> original, @Share("context") LocalRef<Context> reference) {
		if (instance.server_stats$isResultSlot()) {
			ItemStack stack = instance.inventory.getStack(instance.slot);

			if (stack != null) {
				reference.set(new Context(stack));
			}
		}

		return instance.inventory;
	}

	/**
	 * Update the player's statistics with the amount created when taking a stack out of a crafting / furnace output slot.
	 */
	@Inject(
		method = "mouseClicked",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/inventory/slot/InventorySlot;onStackRemovedByPlayer()V"
		)
	)
	private void onStackRemovedByPlayer(CallbackInfo callbackInfo, @Share("context") LocalRef<Context> reference) {
		Context context = reference.get();

		if (context != null) {
			this.awardStatistics(this.minecraft.f_6058446, context.menuStack, context.size);
		}
	}

	@Unique
	private void awardStatistics(PlayerEntity player, ItemStack stack, int amount) {
		int itemId = stack.itemId;

		if (itemId == Item.BREAD.id) {
			player.unlock(Achievements.CRAFT_BREAD);
		}

		if (itemId == Block.FURNACE.id) {
			player.unlock(Achievements.CRAFT_FURNACE);
		}

		if (itemId == Item.IRON_INGOT.id) {
			player.unlock(Achievements.GET_IRON_INGOT);
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
