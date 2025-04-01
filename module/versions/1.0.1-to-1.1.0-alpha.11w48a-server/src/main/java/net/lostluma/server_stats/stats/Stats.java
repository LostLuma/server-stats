package net.lostluma.server_stats.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.crafting.CraftingManager;
import net.minecraft.crafting.recipe.CraftingRecipe;
import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.smelting.SmeltingManager;

/*
 * A cut-down and simplified version of the vanilla 1.7.10 Stats class.
 */
@SuppressWarnings({ "squid:S3008", "squid:S2386" })
public class Stats {
    protected static Map<String, Stat> BY_KEY = new HashMap<>();
    protected static Map<Integer, Stat> BY_VANILLA_ID = new HashMap<>();

    public static List<Stat> ALL = new ArrayList<>();
    public static List<Stat> GENERAL = new ArrayList<>();
    public static List<Stat> USED = new ArrayList<>();
    public static List<Stat> MINED = new ArrayList<>();

    public static Stat GAMES_LEFT = new GeneralStat("stat.leaveGame", 1004).register();
    public static Stat MINUTES_PLAYED = new GeneralStat("stat.playOneMinute", 1100).register();
    public static Stat CM_WALKED = new GeneralStat("stat.walkOneCm", 2000).register();
    public static Stat CM_SWUM = new GeneralStat("stat.swimOneCm", 2001).register();
    public static Stat CM_FALLEN = new GeneralStat("stat.fallOneCm", 2002).register();
    public static Stat CM_CLIMB = new GeneralStat("stat.climbOneCm", 2003).register();
    public static Stat CM_FLOWN = new GeneralStat("stat.flyOneCm", 2004).register();
    public static Stat CM_DIVEN = new GeneralStat("stat.diveOneCm", 2005).register();
    public static Stat CM_MINECART = new GeneralStat("stat.minecartOneCm", 2006).register();
    public static Stat CM_SAILED = new GeneralStat("stat.boatOneCm", 2007).register();
    public static Stat CM_PIG = new GeneralStat("stat.pigOneCm", 2008).register();
    public static Stat JUMPS = new GeneralStat("stat.jump", 2010).register();
    public static Stat DROPS = new GeneralStat("stat.drop", 2011).register();
    public static Stat DAMAGE_DEALT = new GeneralStat("stat.damageDealt", 2020).register();
    public static Stat DAMAGE_TAKEN = new GeneralStat("stat.damageTaken", 2021).register();
    public static Stat DEATHS = new GeneralStat("stat.deaths", 2022).register();
    public static Stat MOBS_KILLED = new GeneralStat("stat.mobKills", 2023).register();
    public static Stat PLAYERS_KILLED = new GeneralStat("stat.playerKills", 2024).register();
    public static Stat FISH_CAUGHT = new GeneralStat("stat.fishCaught", 2025).register();

    public static final Stat[] BLOCKS_MINED = new Stat[4096];
    public static final Stat[] ITEMS_CRAFTED = new Stat[32000];
    public static final Stat[] ITEMS_USED = new Stat[32000];
    public static final Stat[] ITEMS_BROKEN = new Stat[32000];

    // For some reason vanilla stat IDs start at these numbers
    private static final Integer MINE_BLOCK_PREFIX = 16777216;
    private static final Integer USE_ITEM_PREFIX = 16908288;
    private static final Integer BREAK_ITEM_PREFIX = 16973824;
    private static final Integer CRAFT_ITEM_PREFIX = 16842752;

    public static void init() {
        initBlocksMinedStats();
        initItemsUsedStats();
        initItemsBrokenStats();
        initItemsCraftedStats();
    }

    private static void initItemsCraftedStats() {
        HashSet<Item> items = new HashSet<>();

        for (Object item : CraftingManager.getInstance().getRecipes()) {
            CraftingRecipe recipe = (CraftingRecipe) (Object) item;

            if (recipe.getResult() != null) {
                items.add(recipe.getResult().getItem());
            }
        }

        for (Object itemStack : SmeltingManager.getInstance().getRecipes().values()) {
            items.add(((ItemStack) (Object) itemStack).getItem());
        }

        for (Item item : items) {
            if (item != null) {
                int id = item.id;
                ITEMS_CRAFTED[id] = new Stat("stat.craftItem." + id, CRAFT_ITEM_PREFIX + id).register();
            }
        }

        mergeBlockStats(ITEMS_CRAFTED);
    }

    private static void initBlocksMinedStats() {
        for (Block block : Block.BY_ID) {
            if (block == null) {
                continue;
            }

            int id = block.id;

            if (id != 0 && block.hasStats()) {
                BLOCKS_MINED[id] = new Stat("stat.mineBlock." + id, MINE_BLOCK_PREFIX + id).register();
                MINED.add(BLOCKS_MINED[id]);
            }
        }

        mergeBlockStats(BLOCKS_MINED);
    }

    private static void initItemsUsedStats() {
        for (Item item : Item.BY_ID) {
            if (item == null) {
                continue;
            }

            int id = item.id;
            ITEMS_USED[id] = new Stat("stat.useItem." + id, USE_ITEM_PREFIX + id).register();
            if (!(item instanceof BlockItem)) {
                USED.add(ITEMS_USED[id]);
            }

        }

        mergeBlockStats(ITEMS_USED);
    }

    private static void initItemsBrokenStats() {
        for (Item item : Item.BY_ID) {
            if (item == null) {
                continue;
            }

            int id = item.id;
            if (item.isDamageable()) {
                ITEMS_BROKEN[id] = new Stat("stat.breakItem." + id, BREAK_ITEM_PREFIX + id).register();
            }
        }

        mergeBlockStats(ITEMS_BROKEN);
    }

    private static void mergeBlockStats(Stat[] stats) {
        mergeBlockStats(stats, Block.WATER, Block.FLOWING_WATER);
        mergeBlockStats(stats, Block.LAVA, Block.FLOWING_LAVA);
        mergeBlockStats(stats, Block.LIT_PUMPKIN, Block.PUMPKIN);
        mergeBlockStats(stats, Block.LIT_FURNACE, Block.FURNACE);
        mergeBlockStats(stats, Block.LIT_REDSTONE_ORE, Block.REDSTONE_ORE);
        mergeBlockStats(stats, Block.POWERED_REPEATER, Block.REPEATER);
        // mergeBlockStats(stats, Block.POWERED_COMPARATOR, Block.COMPARATOR);
        mergeBlockStats(stats, Block.REDSTONE_TORCH, Block.UNLIT_REDSTONE_TORCH);
        // mergeBlockStats(stats, Block.LIT_REDSTONE_LAMP, Block.REDSTONE_LAMP);
        mergeBlockStats(stats, Block.RED_MUSHROOM, Block.BROWN_MUSHROOM);
        mergeBlockStats(stats, Block.DOUBLE_STONE_SLAB, Block.STONE_SLAB);
        // mergeBlockStats(stats, Block.DOUBLE_WOODEN_SLAB, Block.WOODEN_SLAB);
        mergeBlockStats(stats, Block.GRASS, Block.DIRT);
        mergeBlockStats(stats, Block.FARMLAND, Block.DIRT);
    }

    private static void mergeBlockStats(Stat[] stats, Block block1, Block block2) {
        int id1 = block1.id;
        int id2 = block2.id;
        if (stats[id1] != null && stats[id2] == null) {
            stats[id2] = stats[id1];
        } else {
            ALL.remove(stats[id1]);
            MINED.remove(stats[id1]);
            GENERAL.remove(stats[id1]);
            stats[id1] = stats[id2];
        }
    }

    public static Stat createEntityKillStat(String key) {
        return new Stat("stat.killEntity." + key, null).register();
    }

    public static Stat createKilledByEntityStat(String key) {
        return new Stat("stat.entityKilledBy." + key, null).register();
    }

    public static Stat byKey(String key) {
        return BY_KEY.get(key);
    }

    public static Stat byVanillaId(Integer id) {
        return BY_VANILLA_ID.get(id);
    }

    public static Stat getEntityKillStat(Entity entity) {
        return byKey("stat.killEntity." + Entities.getKey(entity));
    }

    public static Stat getKilledByEntityStat(Entity entity) {
        return byKey("stat.entityKilledBy." + Entities.getKey(entity));
    }
}
