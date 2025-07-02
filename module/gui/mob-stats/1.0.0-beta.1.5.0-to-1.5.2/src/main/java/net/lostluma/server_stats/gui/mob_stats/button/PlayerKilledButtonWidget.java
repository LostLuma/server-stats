package net.lostluma.server_stats.gui.mob_stats.button;

import net.lostluma.server_stats.gui.PlayerFace;
import net.lostluma.server_stats.gui.TextureLocation;
import net.lostluma.server_stats.gui.TooltipConsumer;
import net.minecraft.client.Minecraft;

public class PlayerKilledButtonWidget extends IconButtonWidget {
	private final PlayerFace playerFace;

	public PlayerKilledButtonWidget(int id, int x, int y, PlayerFace playerFace, TextureLocation icon, TooltipConsumer screen, String... tooltip) {
		super(id, x, y, icon, screen, tooltip);

		this.playerFace = playerFace;
	}

	@Override
	public void renderIcon(Minecraft minecraft) {
		this.playerFace.render(this.x + 1, this.y + 1);

		super.renderIcon(minecraft);
	}
}
