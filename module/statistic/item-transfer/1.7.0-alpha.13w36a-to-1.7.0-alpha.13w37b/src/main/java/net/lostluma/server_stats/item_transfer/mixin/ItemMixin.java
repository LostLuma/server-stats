package net.lostluma.server_stats.item_transfer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lostluma.server_stats.item_transfer.duck.DuckIdRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.registry.IdRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class ItemMixin {
	@WrapOperation(
		method = "<clinit>",
		at = @At(value = "NEW", target = "()Lnet/minecraft/util/registry/IdRegistry;")
	)
	private static IdRegistry init(Operation<IdRegistry> original) {
		IdRegistry result = original.call();
		((DuckIdRegistry) result).server_stats$setIsItemRegistry();
		return result;
	}
}
