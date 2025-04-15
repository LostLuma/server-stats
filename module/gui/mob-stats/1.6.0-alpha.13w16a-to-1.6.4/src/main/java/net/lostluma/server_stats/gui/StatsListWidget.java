package net.lostluma.server_stats.gui;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.lostluma.server_stats.gui.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ListWidget;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public abstract class StatsListWidget<T extends StatsListWidget.AbstractStatEntry, C extends StatsListWidget.AbstractColumn<T>> extends ListWidget {
	protected final List<T> entries;
	protected final int headerX;
	protected final int headerY;
	private final Collection<? extends ButtonWidget> buttons;
	private final Minecraft minecraft;
	private C currentColumn;
	private Order order;
	private int selectedEntry;
	private int mouseX;
	private int mouseY;

	public StatsListWidget(Minecraft minecraft, int width, int height, int minY, int maxY, int entryHeight, TooltipConsumer screen) {
		super(minecraft, width, height, minY, maxY, entryHeight);

		this.selectedEntry = -1;
		this.headerX = width / 2 - 92 - 16;
		this.headerY = this.minY - 20;
		this.minecraft = minecraft;
		this.order = this.defaultOrder();

		this.entries = this.createEntries(minecraft, screen);
		this.setColumn(this.defaultColumn());

		this.buttons = this.createHeaderButtons(minecraft, screen);
	}

	protected abstract List<T> createEntries(Minecraft minecraft, TooltipConsumer screen);

	protected abstract C defaultColumn();

	protected abstract Order defaultOrder();

	protected abstract Collection<ButtonWidget> createHeaderButtons(Minecraft minecraft, TooltipConsumer screen);

	protected void setColumn(C column) {
		if (column != this.currentColumn) {
			this.currentColumn = column;
			this.order = Order.DESCENDING;
		} else if (this.order == Order.ASCENDING) {
			this.currentColumn = this.defaultColumn();
			this.order = this.defaultOrder();
		} else {
			this.order = Order.ASCENDING;
		}

		this.entries.sort(this.order == Order.DESCENDING ? this.currentColumn.reversed() : this.currentColumn);
	}

	public void addButtons(Consumer<ButtonWidget> buttonAdder) {
		// TODO: Split stat lists into separate screens so they don't need to be cached & disabled/enabled

		for (ButtonWidget button : this.buttons) {
			buttonAdder.accept(button);
		}
	}

	public void showButtons() {
		for (ButtonWidget button : this.buttons) {
			button.visible = true;
			button.active = true;
		}
	}

	public void hideButtons() {
		for (ButtonWidget button : this.buttons) {
			button.active = false;
			button.visible = false;
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;

		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	protected void renderEntry(int index, int x, int y, int entryHeight, BufferBuilder bufferBuilder) {
		boolean isRowEven = index % 2 == 0;
		int color = isRowEven ? 0xffffffff : 0xff909090;

		this.entries.get(index).render(x, y - 1, this.mouseX, this.mouseY, color);
	}

	public void renderOrderArrow() {
		if (!this.currentColumn.showOrderArrow()) {
			return;
		}

		this.order.texture.bind(this.minecraft);
		DrawUtil.drawTexture(this.headerX + this.currentColumn.xOffset() - 18 * 2, this.headerY, 0, 0, 18, 18, 18, 18, 1.0F);
	}

	@Override
	protected int getEntriesSize() {
		return this.entries.size();
	}

	@Override
	protected void selectEntry(int index, boolean doubleClicked) {
		this.selectedEntry = index;
	}

	@Override
	protected boolean isEntrySelected(int index) {
		return index == this.selectedEntry;
	}

	@Override
	protected void renderBackground() {
	}

	public enum Order {
		ASCENDING(TextureLocation.of("server_stats", "textures/gui/up_arrow.png")),
		DESCENDING(TextureLocation.of("server_stats", "textures/gui/down_arrow.png"));

		private final TextureLocation texture;

		Order(TextureLocation textureXOffset) {
			this.texture = textureXOffset;
		}
	}

	public interface AbstractStatEntry {
		void render(int x, int y, int mouseX, int mouseY, int rowColor);
	}

	public interface AbstractColumn<T> extends Comparator<T> {
		boolean showOrderArrow();

		int xOffset();
	}
}
