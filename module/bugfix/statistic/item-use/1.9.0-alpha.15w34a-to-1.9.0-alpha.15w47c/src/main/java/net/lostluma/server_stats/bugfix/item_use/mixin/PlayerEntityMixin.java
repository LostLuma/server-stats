package net.lostluma.server_stats.bugfix.item_use.mixin;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow
	public abstract void incrementStat(Stat stat);

	@Inject(
		method = "m_9120351",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;damageAndBreak(ILnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void damageShield(CallbackInfo callbackInfo) {
		this.incrementStat(Stats.itemUsed(Item.getId(Items.SHIELD)));
	}
}
