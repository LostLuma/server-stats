package net.lostluma.server_stats.gui.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.lostluma.server_stats.common.util.Format;
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
		ServerStat serverStat = ServerStats.byVanillaId(stat.id);

		if (serverStat == null) {
			return;
		}

		TextRenderer textRenderer = Minecraft.INSTANCE.textRenderer;

		String text;
		long value = Minecraft.INSTANCE.stats.server_stats$value(serverStat);

		if (stat.formatter == Stat.NUMBER_FORMATTER) {
			text = Format.formatNumber(value);
		} else if (stat.formatter == Stat.TIME_FORMATTER) {
			text = Format.formatTime(value);
		} else if (stat.formatter == Stat.DISTANCE_FORMATTER) {
			text = Format.formatDistance(value);
		} else {
			return;
		}

		textRenderer.draw(stat.toString(), x + 2, y + 1, index % 2 == 0 ? 16777215 : 9474192, true);
		textRenderer.draw(text, x + 2 + 213 - textRenderer.getStringWidth(text), y + 1, index % 2 == 0 ? 16777215 : 9474192, true);

		callbackInfo.cancel();
	}
}
