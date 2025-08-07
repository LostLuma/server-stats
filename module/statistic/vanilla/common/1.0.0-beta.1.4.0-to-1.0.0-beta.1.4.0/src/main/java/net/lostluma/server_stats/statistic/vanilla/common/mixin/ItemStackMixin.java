package net.lostluma.server_stats.statistic.vanilla.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Shadow
	public int itemId;

	@WrapOperation(
		method = "attackEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;attackEntity(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/entity/living/LivingEntity;)Z"
		)
	)
	private boolean attackEntity(Item instance, ItemStack stack, LivingEntity target, LivingEntity source, Operation<Boolean> original) {
		boolean result = original.call(instance, stack, target, source);

		if (source instanceof PlayerEntity && result) {
			((PlayerEntity) source).increment(Statistics.useItem(this.itemId));
		}

		return result;
	}

	@WrapOperation(
		method = "mineBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;mineBlock(Lnet/minecraft/item/ItemStack;IIIILnet/minecraft/entity/living/LivingEntity;)Z"
		)
	)
	private boolean mineBlock(Item instance, ItemStack stack, int x, int y, int z, int face, LivingEntity entity, Operation<Boolean> original) {
		boolean result = original.call(instance, stack, x, y, z, face, entity);

		if (entity instanceof PlayerEntity && result) {
			((PlayerEntity) entity).increment(Statistics.useItem(this.itemId));
		}

		return result;
	}

	@WrapOperation(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;use(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/player/PlayerEntity;Lnet/minecraft/world/World;IIII)Z"
		)
	)
	private boolean use(Item instance, ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int face, Operation<Boolean> original) {
		boolean result = original.call(instance, stack, player, world, x, y, z, face);

		if (result) {
			player.increment(Statistics.useItem(this.itemId));
		}

		return result;
	}

	@Inject(
		method = "damageAndBreak",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/item/ItemStack;size:I",
			opcode = Opcodes.PUTFIELD
		)
	)
	private void damageAndBreak(int damage, Entity entity, CallbackInfo callbackInfo) {
		if (entity instanceof PlayerEntity) {
			((PlayerEntity) entity).increment(Statistics.breakItem(this.itemId));
		}
	}
}
