package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.UUIDHelper;
import net.minecraft.server.stat.ServerPlayerStats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(ServerPlayerStats.class)
public class ServerPlayerStatsMixin {
	@Shadow
	@Final
	@Mutable
	private File file;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		try {
			this.server_stats$moveFile();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create UUID-based statistics file.");
		}
	}

	@Unique
	private void server_stats$moveFile() throws IOException {
		Path name = this.file.toPath();
		Path parent = name.getParent();

		String username = this.server_stats$username();
		Path uuid = parent.resolve(UUIDHelper.getUuid(username) + ".json");

		if (Files.exists(name)) {
			Files.move(name, uuid);
		}

		this.file = uuid.toFile();
	}

	@Unique
	private String server_stats$username() {
		return this.file.getName().replace(".json", "");
	}
}
