package net.lostluma.server_stats.mixin.common;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {
	@Shadow
	public List<PlayerEntity> players;

    @Inject(method = "saveData", at = @At("TAIL"))
    public void onSave(CallbackInfo callbackInfo) {
    	for (PlayerEntity player : this.players) {
            player.server_stats$saveStats();
        }
    }
}
