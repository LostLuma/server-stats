package net.lostluma.server_stats.bugfix.game_directory.mixin;

import net.lostluma.server_stats.util.platform.Platform;
import net.minecraft.client.C_5664496;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

@Mixin(C_5664496.class)
public class MinecraftMixin {
	/**
	 * Fix the game not respecting the current working directory.
	 * <br>
	 * By default, the game directory is always {@code ~/.minecraft} instead.
	 */
	@Shadow
	@SuppressWarnings("unused")
	private File f_9479876 = Platform.getGameDir().toFile();
}
