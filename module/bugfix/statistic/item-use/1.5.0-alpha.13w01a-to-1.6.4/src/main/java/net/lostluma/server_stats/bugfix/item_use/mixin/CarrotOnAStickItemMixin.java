package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.CarrotOnAStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CarrotOnAStickItem.class)
public class CarrotOnAStickItemMixin extends Item {
	protected CarrotOnAStickItemMixin(int id) {
		super(id);
	}

	/**
	 * Count item use when feeding a pig while riding it.
	 */
	@Inject(
		method = "startUsing",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;damageAndBreak(ILnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void startUsing(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> callbackInfo) {
		player.incrementStat(Stats.ITEMS_USED[this.id]);
	}
}
