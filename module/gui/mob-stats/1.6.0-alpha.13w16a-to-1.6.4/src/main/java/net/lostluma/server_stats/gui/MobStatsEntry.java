package net.lostluma.server_stats.gui;

import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.lostluma.server_stats.common.util.Format;
import net.lostluma.server_stats.gui.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import org.jetbrains.annotations.Nullable;

public class MobStatsEntry extends GuiElement implements StatsListWidget.AbstractStatEntry {
    private final String entityName;
    private final TextureLocation entityIcon;
    private final long kills;
    private final long killedBy;

    private final TooltipConsumer parent;

    private final Minecraft minecraft;

    public MobStatsEntry(String entityName, TextureLocation entityIcon, long killedBy, long kills, TooltipConsumer parent, Minecraft minecraft) {
        this.entityName = entityName;
        this.entityIcon = entityIcon;
        this.kills = kills;
        this.killedBy = killedBy;
        this.parent = parent;
        this.minecraft = minecraft;
    }

    public static @Nullable MobStatsEntry forEntityId(String entityId, Minecraft minecraft, TooltipConsumer tooltipConsumer) {
        ServerStat killsStat = ServerStats.getEntityKillStat(entityId);
        ServerStat killedByStat = ServerStats.getKilledByEntityStat(entityId);

        if (killsStat == null || killedByStat == null) {
            return null;
        }

        String entityName = MobStatsUtil.getDisplayName(entityId);
		TextureLocation entityIcon = TextureLocation.of("server_stats", "textures/mob_face/" + MobStatsUtil.separateWith('_', entityId).toLowerCase() + ".png");
        long kills = minecraft.stats.server_stats$value(killsStat);
        long killedBy = minecraft.stats.server_stats$value(killedByStat);

        return new MobStatsEntry(entityName, entityIcon, killedBy, kills, tooltipConsumer, minecraft);
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY, int rowColor) {
        String kills = Format.formatNumber(this.kills());
        String killedBy = Format.formatNumber(this.killedBy);

        this.drawString(this.minecraft.textRenderer, kills, x + MobStatsListWidget.Column.KILLS.xOffset() - this.minecraft.textRenderer.getStringWidth(kills), y + 5, rowColor);
        this.drawString(this.minecraft.textRenderer, killedBy, x + MobStatsListWidget.Column.KILLED_BY.xOffset() - this.minecraft.textRenderer.getStringWidth(killedBy), y + 5, rowColor);

        this.renderIcon(x + 40, y, mouseX, mouseY, rowColor);
    }

    private void renderIcon(int x, int y, int mouseX, int mouseY, int borderColor) {
        DrawUtil.drawFrameWithTooltip(x, y, 18, 18, borderColor, mouseX, mouseY, this.parent, this.entityName());

		this.entityIcon.bind(this.minecraft);
        DrawUtil.drawTexture(x + 1, y + 1, 0, 0, 16, 16, 16, 16, 1);
    }

    public String entityName() {
        return this.entityName;
    }

    public long kills() {
        return this.kills;
    }

    public long killedBy() {
        return this.killedBy;
    }
}
