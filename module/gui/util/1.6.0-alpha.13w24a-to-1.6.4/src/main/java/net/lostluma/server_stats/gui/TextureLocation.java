package net.lostluma.server_stats.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.Identifier;

public class TextureLocation {
	private final Identifier representation;

	TextureLocation(Identifier representation) {
		this.representation = representation;
	}

	public static TextureLocation of(String namespace, String path) {
		Identifier representation = new Identifier(namespace, path);
		return new TextureLocation(representation);
	}

	public void bind(Minecraft minecraft) {
		minecraft.getTextureManager().bind(this.representation);
	}
}
