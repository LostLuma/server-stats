package net.lostluma.server_stats.shared.common;

import net.fabricmc.api.EnvType;
import org.objectweb.asm.tree.ClassNode;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.Version;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ServerStatsSharedMixinConfig implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		Version version = Version.of("1.3");
		ModContainer container = QuiltLoader.getModContainer("minecraft").get();

		// Load the ServerPlayerEntityMixin conditionally
		// On servers always, otherwise only on versions >= 1.3

		if (container.metadata().version().compareTo(version) > -1) {
			return true; // Post 1.3 (has internal server)
		} else if (MinecraftQuiltLoader.getEnvironmentType() == EnvType.SERVER) {
			return true; // Dedicated server, always load.
		} else {
			return !mixinClassName.contains("ServerPlayerEntityMixin");
		}
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public List<String> getMixins() {
		return Collections.emptyList();
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
