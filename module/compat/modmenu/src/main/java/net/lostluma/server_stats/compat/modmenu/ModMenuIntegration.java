package net.lostluma.server_stats.compat.modmenu;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.UpdateChecker;
import net.lostluma.server_stats.util.Constants;

import java.util.Map;

public class ModMenuIntegration implements ModMenuApi {
	@Override
	public Map<String, UpdateChecker> getProvidedUpdateCheckers() {
		return ImmutableMap.of(Constants.MOD_ID, new ServerStatsUpdateChecker());
	}
}
