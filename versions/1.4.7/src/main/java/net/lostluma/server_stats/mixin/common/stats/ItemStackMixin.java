package net.lostluma.server_stats.mixin.common.stats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void use(PlayerEntity player, World world, int x, int y, int z, int face, float dx, float dy, float dz, CallbackInfoReturnable<?> callbackInfo) {
        var stack = (ItemStack)(Object)this;
        player.server_stats$incrementStat(Stats.ITEMS_USED[stack.itemId], 1);
    }

    @Inject(method = "damageAndBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void damageAndBreak(int amount, LivingEntity entity, CallbackInfo callbackInfo) {
        var stack = (ItemStack)(Object)this;
        var player = (PlayerEntity)(Object)entity;

        player.server_stats$incrementStat(Stats.ITEMS_BROKEN[stack.itemId], 1);
    }

    @Inject(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void attackEntity(LivingEntity entity, PlayerEntity player, CallbackInfo callbackInfo) {
        var stack = (ItemStack)(Object)this;
        player.server_stats$incrementStat(Stats.ITEMS_USED[stack.itemId], 1);
    }

    @Inject(method = "mineBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void mineBlock(World world, int blockId, int x, int y, int z, PlayerEntity player, CallbackInfo callbackInfo) {
        var stack = (ItemStack)(Object)this;
        player.server_stats$incrementStat(Stats.ITEMS_USED[stack.itemId], 1);
    }

    @Inject(method = "onResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void onResult(World world, PlayerEntity player, int amount, CallbackInfo callbackInfo) {
        var stack = (ItemStack)(Object)this;
        player.server_stats$incrementStat(Stats.ITEMS_CRAFTED[stack.itemId], amount);
    }
}
