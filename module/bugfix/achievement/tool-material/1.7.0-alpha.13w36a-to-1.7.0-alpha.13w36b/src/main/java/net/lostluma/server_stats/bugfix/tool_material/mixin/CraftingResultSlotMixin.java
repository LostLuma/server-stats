package net.lostluma.server_stats.bugfix.tool_material.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.slot.CraftingResultSlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.stat.achievement.Achievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {
	@Shadow
	private PlayerEntity player;

	/**
	 * Allow unlocking crafting-related achievements using all materials.
	 */
	@Inject(method = "checkAchievements", at = @At("HEAD"))
	private void onStackRemovedByPlayer(ItemStack stack, CallbackInfo callbackInfo) {
		int itemId = Item.getId(stack.getItem());

		if (stack.getItem() instanceof HoeItem && itemId != Item.getId(Items.WOODEN_HOE)) {
			this.player.incrementStat(Achievements.CRAFT_WOODEN_HOE);
		}

		if (stack.getItem() instanceof SwordItem && itemId != Item.getId(Items.WOODEN_SWORD)) {
			this.player.incrementStat(Achievements.CRAFT_SWORD);
		}

		if (stack.getItem() instanceof PickaxeItem && itemId != Item.getId(Items.WOODEN_PICKAXE)) {
			this.player.incrementStat(Achievements.CRAFT_PICKAXE);

			if (itemId != Item.getId(Items.STONE_PICKAXE)) {
				this.player.incrementStat(Achievements.CRAFT_BETTER_PICKAXE);
			}
		}
	}
}
