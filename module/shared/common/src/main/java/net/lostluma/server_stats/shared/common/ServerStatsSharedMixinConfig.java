package net.lostluma.server_stats.shared.common;

import net.lostluma.server_stats.common.util.Platform;
import net.lostluma.server_stats.common.util.Version;
import org.objectweb.asm.tree.ClassNode;
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

		// Load the ServerPlayerEntityMixin conditionally
		// On servers always, otherwise only on versions >= 1.3

		if (Platform.getModVersion("minecraft").compareTo(version) > -1) {
			return true; // Post 1.3 (has internal server)
		} else if (Platform.getEnvironment() == Platform.Environment.SERVER) {
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
