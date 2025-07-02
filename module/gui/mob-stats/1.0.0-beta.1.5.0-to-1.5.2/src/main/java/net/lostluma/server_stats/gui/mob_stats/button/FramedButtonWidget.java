package net.lostluma.server_stats.gui.mob_stats.button;

import net.lostluma.server_stats.gui.TooltipConsumer;
import net.lostluma.server_stats.gui.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public abstract class FramedButtonWidget extends ButtonWidget implements CallbackButton {
	private final int frameColor;
	private final TooltipConsumer screen;
	private final String[] tooltip;

	public FramedButtonWidget(int id, int x, int y, int frameColor, TooltipConsumer screen, String... tooltip) {
		super(id, x, y, 18, 18, null);

		this.frameColor = frameColor;
		this.screen = screen;
		this.tooltip = tooltip;
	}

	public abstract void renderIcon(Minecraft minecraft);

	@Override
	public void render(Minecraft minecraft, int mouseX, int mouseY) {
		if (!this.visible) {
			return;
		}

		DrawUtil.drawFrameWithTooltip(x, y, this.width, this.height, this.frameColor, mouseX, mouseY, this.screen, this.tooltip);
		this.renderIcon(minecraft);
	}
}
