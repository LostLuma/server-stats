package net.lostluma.server_stats.gui.large_stats.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.Format;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.stat.ItemStat;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/gui/screen/StatsScreen$AbstractStatsListWidget")
public class AbstractStatsListWidgetMixin {
	@Inject(method = "m_0499830", at = @At("HEAD"), cancellable = true)
	private void a(ItemStat itemStat, int i, int j, boolean bl, CallbackInfo callbackInfo) {
		if (itemStat == null) {
			return;
		}

		ServerStatistic serverStat = ServerStatistic.from(itemStat);

		String text;
		long value = Minecraft.INSTANCE.stats.get(serverStat);

		if (itemStat.formatter == Stat.NUMBER_FORMATTER) {
			text = Format.formatNumber(value);
		} else if (itemStat.formatter == Stat.TIME_FORMATTER) {
			text = Format.formatTime(value);
		} else if (itemStat.formatter == Stat.DISTANCE_FORMATTER) {
			text = Format.formatDistance(value);
		} else {
			return;
		}

		TextRenderer textRenderer = Minecraft.INSTANCE.textRenderer;
		textRenderer.draw(text, i - textRenderer.getStringWidth(text), j + 5, bl ? 16777215 : 9474192, true);

		callbackInfo.cancel();
	}
}
