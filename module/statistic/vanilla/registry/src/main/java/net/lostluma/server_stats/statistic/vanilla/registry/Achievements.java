package net.lostluma.server_stats.statistic.vanilla.registry;

import net.lostluma.server_stats.api.v1.statistic.ServerAchievement;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import org.jetbrains.annotations.Nullable;

public class Achievements {
	public static final ServerAchievement OPEN_INVENTORY = ServerAchievement.of("minecraft", "openInventory").build();
	public static final ServerAchievement GET_LOG = ServerAchievement.of("minecraft", "mineWood").parent(OPEN_INVENTORY).build();
	public static final ServerAchievement CRAFT_CRAFTING_TABLE = ServerAchievement.of("minecraft", "buildWorkBench").parent(GET_LOG).build();
	public static final ServerAchievement CRAFT_PICKAXE = ServerAchievement.of("minecraft", "buildPickaxe").parent(CRAFT_CRAFTING_TABLE).build();
	public static final ServerAchievement CRAFT_FURNACE = ServerAchievement.of("minecraft", "buildFurnace").parent(CRAFT_PICKAXE).build();
	public static final ServerAchievement GET_IRON_INGOT = ServerAchievement.of("minecraft", "acquireIron").parent(CRAFT_FURNACE).build();
	public static final ServerAchievement CRAFT_WOODEN_HOE = ServerAchievement.of("minecraft", "buildHoe").parent(CRAFT_CRAFTING_TABLE).build();
	public static final ServerAchievement CRAFT_BREAD = ServerAchievement.of("minecraft", "makeBread").parent(CRAFT_WOODEN_HOE).build();
	public static final ServerAchievement CRAFT_CAKE = registerConditionally("bakeCake", "1.0.0-beta.2", CRAFT_WOODEN_HOE);
	public static final ServerAchievement CRAFT_BETTER_PICKAXE = ServerAchievement.of("minecraft", "buildBetterPickaxe").parent(CRAFT_PICKAXE).build();
	public static final ServerAchievement COOK_FISH = registerConditionally("cookFish", "1.0.0-alpha.2.0", CRAFT_FURNACE);
	public static final ServerAchievement TRAVEL_KILOMETER_BY_MINECART = registerConditionally("onARail", "0.31.20100624", GET_IRON_INGOT);
	public static final ServerAchievement CRAFT_SWORD = ServerAchievement.of("minecraft", "buildSword").parent(CRAFT_CRAFTING_TABLE).build();
	public static final ServerAchievement KILL_ENEMY = ServerAchievement.of("minecraft", "killEnemy").parent(CRAFT_SWORD).build();
	public static final ServerAchievement KILL_COW = ServerAchievement.of("minecraft", "killCow").parent(CRAFT_SWORD).build();
	public static final ServerAchievement RIDE_PIG_OFF_CLIFF = registerConditionally("flyPig", "0.31.20100625-1917", KILL_COW);

	public static void init() {}

	private static @Nullable ServerAchievement registerConditionally(String identifier, String startVersion, ServerAchievement parent) {
		Version want = Version.of(startVersion);
		Version game = Platform.getModVersion("minecraft");

		if (want.compareTo(game) >= 0) {
			return null;
		} else {
			return ServerAchievement.of("minecraft", identifier).parent(parent).build();
		}
	}
}
