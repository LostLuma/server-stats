package net.lostluma.server_stats.gui;

import net.minecraft.client.Minecraft;

public class TextureLocation {
	private final String representation;

	TextureLocation(String representation) {
		this.representation = representation;
	}

	public static TextureLocation of(String namespace, String path) {
		String representation = "/assets/" + namespace + "/" + path;
		return new TextureLocation(representation);
	}

	public void bind(Minecraft minecraft) {
		minecraft.textureManager.bind(minecraft.textureManager.load(this.representation));
	}
}
