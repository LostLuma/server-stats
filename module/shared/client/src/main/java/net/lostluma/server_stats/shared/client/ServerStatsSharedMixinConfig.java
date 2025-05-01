package net.lostluma.server_stats.shared.client;

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

		// Load the LocalPlayerEntityMixin conditionally
		// This mixin is only required on versions before 1.3

		if (Platform.getModVersion("minecraft").compareTo(version) < 1) {
			return true; // Pre 1.3 (no internal server)
		} else {
			return !mixinClassName.contains("LocalPlayerEntityMixin");
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
