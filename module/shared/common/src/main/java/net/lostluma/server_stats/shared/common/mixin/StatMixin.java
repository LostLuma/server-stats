package net.lostluma.server_stats.shared.common.mixin;

import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stat.class)
public class StatMixin {
	@Shadow
	@Final
	public int id;

	@Inject(method = "register", at = @At("RETURN"))
	private void register(CallbackInfoReturnable<Stat> callbackInfo) {
		ServerStats.createStat(this.id);
	}
}
