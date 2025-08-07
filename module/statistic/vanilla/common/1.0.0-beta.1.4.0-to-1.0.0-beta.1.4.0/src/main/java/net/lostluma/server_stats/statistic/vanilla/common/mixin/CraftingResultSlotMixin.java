package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import net.lostluma.server_stats.statistic.vanilla.registry.Achievements;
import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.slot.CraftingResultSlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {
	@Shadow
	private PlayerEntity player;

	@Inject(method = "onStackRemovedByPlayer", at = @At("HEAD"))
	private void onStackRemovedByPlayer(ItemStack stack, CallbackInfo callbackInfo) {
		int itemId = stack.getItem().id;

		if (itemId == Item.CAKE.id) {
			this.player.unlock(Achievements.CRAFT_CAKE);
		}

		if (itemId == Item.BREAD.id) {
			this.player.unlock(Achievements.CRAFT_BREAD);
		}

		if (itemId == Block.FURNACE.id) {
			this.player.unlock(Achievements.CRAFT_FURNACE);
		}

		if (itemId == Block.CRAFTING_TABLE.id) {
			this.player.unlock(Achievements.CRAFT_CRAFTING_TABLE);
		}

		if (stack.getItem() instanceof HoeItem) {
			this.player.unlock(Achievements.CRAFT_WOODEN_HOE);
		}

		if (stack.getItem() instanceof SwordItem) {
			this.player.unlock(Achievements.CRAFT_SWORD);
		}

		if (stack.getItem() instanceof PickaxeItem) {
			this.player.unlock(Achievements.CRAFT_PICKAXE);

			if (itemId != Item.WOODEN_PICKAXE.id) {
				this.player.unlock(Achievements.CRAFT_BETTER_PICKAXE);
			}
		}
	}
}
