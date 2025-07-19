package net.lostluma.server_stats.gui.large_stats.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.lostluma.server_stats.util.Format;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/gui/screen/StatsScreen$GeneralStatsListWidget")
public class GeneralStatsListWidgetMixin {
	@Inject(method = "renderEntry", at = @At("HEAD"), cancellable = true)
	private void renderEntry(int index, int x, int y, int unused, BufferBuilder bufferBuilder, CallbackInfo callbackInfo) {
		Stat stat = (Stat) Stats.GENERAL.get(index);
		ServerStatistic serverStat = ServerStatistic.from(stat);

		TextRenderer textRenderer = Minecraft.INSTANCE.textRenderer;

		String text;
		long value = Minecraft.INSTANCE.statHandler.get(serverStat);

		if (stat.formatter == Stat.NUMBER_FORMATTER) {
			text = Format.formatNumber(value);
		} else if (stat.formatter == Stat.TIME_FORMATTER) {
			text = Format.formatTime(value);
		} else if (stat.formatter == Stat.DISTANCE_FORMATTER) {
			text = Format.formatDistance(value);
		} else {
			return;
		}

		textRenderer.draw(stat.toString(), x + 2, y + 1, index % 2 == 0 ? 16777215 : 9474192);
		textRenderer.draw(text, x + 2 + 213 - textRenderer.getStringWidth(text), y + 1, index % 2 == 0 ? 16777215 : 9474192);

		callbackInfo.cancel();
	}
}
