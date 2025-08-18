package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.statistic.vanilla.client.Vanilla;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.client.C_5664496;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5664496.class)
public class MinecraftMixin {
	@Shadow
	public World f_5854988;

	@Shadow
	public InputPlayerEntity f_6058446;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Vanilla.minecraft = (C_5664496)(Object) this;
	}

	@Inject(method = "m_9890357", at = @At("HEAD"))
	private void setWorld(World world, String string, CallbackInfo callbackInfo) {
		if (world == null && this.f_5854988 != null) {
			this.f_6058446.increment(Statistics.GAMES_LEFT);
		}
	}

	@WrapOperation(
		method = "m_1075084",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;attackEntity(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void handleMouseClick0(Item instance, ItemStack stack, LivingEntity target, Operation<Void> original) {
		original.call(instance, stack, target);
		this.f_6058446.increment(Statistics.useItem(stack.itemId));

		if (stack.size == 0) {
			this.f_6058446.increment(Statistics.breakItem(stack.itemId));
		}
	}

	@WrapOperation(
		method = "m_1075084",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;canInteract(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/LivingEntity;)V"
		)
	)
	private void handleMouseClick1(Item instance, ItemStack stack, LivingEntity target, Operation<Void> original) {
		original.call(instance, stack, target);
		this.f_6058446.increment(Statistics.useItem(stack.itemId));

		if (stack.size == 0) {
			this.f_6058446.increment(Statistics.breakItem(stack.itemId));
		}
	}

	@WrapOperation(
		method = "m_1075084",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/Item;use(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/living/player/PlayerEntity;Lnet/minecraft/world/World;IIII)Z"
		)
	)
	private boolean handleMouseClick2(Item instance, ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int face, Operation<Boolean> original) {
		boolean result = original.call(instance, stack, player, world, x, y, z, face);

		if (result) {
			this.f_6058446.increment(Statistics.useItem(stack.itemId));
		}

		return result;
	}
}
