package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// NOTE: PotionItem.finishUsing injection is done in ConsumableItemMixin
@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {
	protected PotionItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when throwing a splash potion.
	 */
	@Inject(
		method = "startUsing",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;Ljava/lang/String;FF)V"
		)
	)
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.incrementStat(Stats.ITEMS_USED[this.id]);
	}
}
