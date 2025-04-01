package net.lostluma.server_stats.bugfix.movement.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "move", at = @At("HEAD"))
    private void move(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        player.server_stats$move();
    }
}
