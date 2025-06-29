package net.lostluma.server_stats.bugfix.tool_material.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.slot.CraftingResultSlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		int itemId = stack.getItem().id;

		if (stack.getItem() instanceof HoeItem && itemId != Item.WOODEN_HOE.id) {
			this.player.incrementStat(Achievements.CRAFT_WOODEN_HOE);
		}

		if (stack.getItem() instanceof SwordItem && itemId != Item.WOODEN_SWORD.id) {
			this.player.incrementStat(Achievements.CRAFT_SWORD);
		}

		if (stack.getItem() instanceof PickaxeItem && itemId != Item.WOODEN_PICKAXE.id) {
			this.player.incrementStat(Achievements.CRAFT_PICKAXE);

			if (itemId != Item.STONE_PICKAXE.id) {
				this.player.incrementStat(Achievements.CRAFT_BETTER_PICKAXE);
			}
		}
	}
}
