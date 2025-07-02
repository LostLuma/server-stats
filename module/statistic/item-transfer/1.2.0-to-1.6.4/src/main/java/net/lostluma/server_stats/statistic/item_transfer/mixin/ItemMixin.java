package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {
	@Shadow
	@Final
	public int id;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		ServerStatistic.of("minecraft", "drop." + this.id).build();
		ServerStatistic.of("minecraft", "pickup." + this.id).build();
	}
}
