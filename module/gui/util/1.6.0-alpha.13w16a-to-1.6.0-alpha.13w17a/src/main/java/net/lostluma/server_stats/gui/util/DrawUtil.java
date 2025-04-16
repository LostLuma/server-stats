package net.lostluma.server_stats.gui.util;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.lostluma.server_stats.gui.TooltipConsumer;
import net.minecraft.client.gui.GuiElement;

public class DrawUtil extends GuiElement {
	private static final DrawUtil INSTANCE = new DrawUtil();

	private static final int FRAME_BACKGROUND_COLOR = 0xa0000000;

	public static void drawFrameWithTooltip(int x, int y, int width, int height, int frameColor, int mouseX, int mouseY, TooltipConsumer tooltipConsumer, String... tooltipLines) {
		drawFrame(x, y, width, height, frameColor);

		if (x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + height) {
			tooltipConsumer.acceptTooltip(tooltipLines);
		}
	}

	public static void drawFrame(int x, int y, int width, int height, int frameColor) {
		INSTANCE.fillGradient(x, y, x + width, y + height, frameColor, frameColor);
		INSTANCE.fillGradient(x + 1, y + 1, x + width - 1, y + height - 1, FRAME_BACKGROUND_COLOR, FRAME_BACKGROUND_COLOR);
	}

	/**
	 * Utility method taken from Ornithe ModMenu.
	 * <a href="https://github.com/OrnitheMC/modmenu/blob/092146bdb5e0094e368a2ed91a098b7398a9d79a/src/main/java/com/terraformersmc/modmenu/util/DrawingUtil.java#L63C1-L73C2">...</a>
	 */
	public static void drawTexture(float x, float y, float u, float v, float width, float height, float scaleU, float scaleV, float alpha) {
		float invertedScaleU = 1.0f / scaleU;
		float invertedScaleV = 1.0f / scaleV;

		BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;

		bufferBuilder.start();
		bufferBuilder.color(255, 255, 255, (int) (255 * alpha)); // Added to allow transparency
		bufferBuilder.vertex(x, y + height, 0.0, u * invertedScaleU, (v + (float) height) * invertedScaleV);
		bufferBuilder.vertex(x + width, y + height, 0.0, (u + (float) width) * invertedScaleU, (v + (float) height) * invertedScaleV);
		bufferBuilder.vertex(x + width, y, 0.0, (u + (float) width) * invertedScaleU, v * invertedScaleV);
		bufferBuilder.vertex(x, y, 0.0, u * invertedScaleU, v * invertedScaleV);
		bufferBuilder.end();
	}
}
