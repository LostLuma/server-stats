package net.lostluma.server_stats.gui.mob_stats;

import net.lostluma.server_stats.gui.PlayerFace;
import net.lostluma.server_stats.gui.TextureLocation;
import net.lostluma.server_stats.gui.TooltipConsumer;
import net.lostluma.server_stats.gui.mob_stats.button.IconButtonWidget;
import net.lostluma.server_stats.gui.mob_stats.button.PlayerKilledButtonWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entities;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MobStatsListWidget extends StatsListWidget<MobStatsEntry, MobStatsListWidget.Column> {
	public static final TextureLocation MOBS_KILLED_ICON = TextureLocation.of("server_stats", "textures/gui/mobs_killed.png");
	public static final TextureLocation PLAYER_KILLED_ICON = TextureLocation.of("server_stats", "textures/gui/kill.png");

	public static final int BUTTON_ID = 3333333;

	public MobStatsListWidget(Minecraft minecraft, TooltipConsumer screen, int width, int height, int minY, int maxY) {
		super(minecraft, width, height, minY + 18, maxY, 20, screen);
	}

	@Override
	@SuppressWarnings("unchecked") // Note: Missing Sparrow
	protected List<MobStatsEntry> createEntries(Minecraft minecraft, TooltipConsumer screen) {
		return ((Stream<String>) Entities.KEY_TO_TYPE.keySet().stream())
			.map(entityId -> MobStatsEntry.forEntityId(entityId, minecraft, screen))
			.filter(Objects::nonNull)
			.filter(entityId -> entityId.kills() > 0 || entityId.killedBy() > 0)
			.collect(Collectors.toList());
	}

	@Override
	protected Column defaultColumn() {
		return Column.MOB_NAME;
	}

	@Override
	protected Order defaultOrder() {
		return Order.ASCENDING;
	}

	@Override
	protected Collection<ButtonWidget> createHeaderButtons(Minecraft minecraft, TooltipConsumer screen) {
		Collection<ButtonWidget> buttons = new ArrayList<>();

		buttons.add(new IconButtonWidget(BUTTON_ID, this.headerX + Column.KILLS.xOffset - 18, this.headerY, MOBS_KILLED_ICON, screen, I18n.translate("server_stats.stats_screen.kills")) {
			@Override
			public void callback() {
				MobStatsListWidget.this.setColumn(Column.KILLS);
			}
		});
		buttons.add(new PlayerKilledButtonWidget(BUTTON_ID, this.headerX + Column.KILLED_BY.xOffset - 18, this.headerY, new PlayerFace(minecraft), PLAYER_KILLED_ICON, screen, I18n.translate("server_stats.stats_screen.killed_by")) {
			@Override
			public void callback() {
				MobStatsListWidget.this.setColumn(Column.KILLED_BY);
			}
		});

		return buttons;
	}

	public enum Column implements StatsListWidget.AbstractColumn<MobStatsEntry> {
		MOB_NAME(Comparator.comparing(MobStatsEntry::entityName), 40),
		KILLS(Comparator.comparingLong(MobStatsEntry::kills), 115, true),
		KILLED_BY(Comparator.comparingLong(MobStatsEntry::killedBy), 165, true);

		private final Comparator<MobStatsEntry> parent;
		private final int xOffset;
		private final boolean showOrderArrow;

		Column(Comparator<MobStatsEntry> parent, int offset) {
			this(parent, offset, false);
		}

		Column(Comparator<MobStatsEntry> parent, int offset, boolean showOrder) {
			this.parent = parent;
			this.xOffset = offset;
			this.showOrderArrow = showOrder;
		}

		@Override
		public int compare(MobStatsEntry o1, MobStatsEntry o2) {
			return this.parent.compare(o1, o2);
		}

		@Override
		public boolean showOrderArrow() {
			return this.showOrderArrow;
		}

		@Override
		public int xOffset() {
			return this.xOffset;
		}
	}
}
