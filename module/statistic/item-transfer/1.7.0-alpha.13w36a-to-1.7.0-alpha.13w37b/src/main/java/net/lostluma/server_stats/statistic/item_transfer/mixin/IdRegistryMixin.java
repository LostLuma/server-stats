package net.lostluma.server_stats.statistic.item_transfer.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.statistic.item_transfer.duck.DuckIdRegistry;
import net.minecraft.util.registry.IdRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IdRegistry.class)
public class IdRegistryMixin implements DuckIdRegistry {
	@Unique
	private boolean server_stats$isItemRegistry;

	@Override
	public void server_stats$setIsItemRegistry() {
		this.server_stats$isItemRegistry = true;
	}

	@Inject(method = "register", at = @At("RETURN"))
	private void register(int id, String key, Object value, CallbackInfo callbackInfo) {
		if (this.server_stats$isItemRegistry) {
			ServerStatistic.of("minecraft", "drop." + id).build();
			ServerStatistic.of("minecraft", "pickup." + id).build();
		}
	}
}
