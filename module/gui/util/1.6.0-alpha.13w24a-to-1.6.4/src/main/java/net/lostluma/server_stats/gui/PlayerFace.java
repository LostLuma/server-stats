package net.lostluma.server_stats.gui;

import net.lostluma.server_stats.gui.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.client.resource.Identifier;
import org.lwjgl.opengl.GL11;

public class PlayerFace {
	private final Identifier skin;

	private final TextureManager textureManager;
	private int textureHeight;

	public PlayerFace(Minecraft minecraft) {
		this.skin = minecraft.player.getSkinTextureLocation();
		this.textureManager = minecraft.getTextureManager();
		this.textureHeight = -1;
	}

	public void render(float x, float y) {
		this.textureManager.bind(this.skin);

		float factor = 8F / 9F;

		// Draw the base layer smaller. All numbers are 8/9ths smaller than the hat
		DrawUtil.drawTexture(x + factor, y + factor, 16F * factor, 16F * factor, 16F * factor, 16F * factor, 128F * factor, this.textureHeight() * 2F * factor, 1);
		DrawUtil.drawTexture(x, y, 80, 16, 16, 16, 128, this.textureHeight() * 2, 1);
	}

	private float textureHeight() {
		if (this.textureHeight == -1) {
			this.textureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		}

		return this.textureHeight;
	}
}
