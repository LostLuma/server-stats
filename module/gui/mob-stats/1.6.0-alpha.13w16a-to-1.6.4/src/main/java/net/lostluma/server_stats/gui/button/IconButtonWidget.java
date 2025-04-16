package net.lostluma.server_stats.gui.button;

import net.lostluma.server_stats.gui.TextureLocation;
import net.lostluma.server_stats.gui.TooltipConsumer;
import net.lostluma.server_stats.gui.util.DrawUtil;
import net.minecraft.client.Minecraft;

public class IconButtonWidget extends FramedButtonWidget {
	private final TextureLocation iconTexture;

	public IconButtonWidget(int id, int x, int y, TextureLocation iconTexture, TooltipConsumer screen, String... tooltip) {
		super(id, x, y, 0xffffffff, screen, tooltip);

		this.iconTexture = iconTexture;
	}

	@Override
	public void renderIcon(Minecraft minecraft) {
		this.iconTexture.bind(minecraft);
		DrawUtil.drawTexture(this.x + 1, this.y + 1, 0, 0, 16, 16, 16, 16, 1);
	}
}
