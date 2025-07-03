package net.lostluma.server_stats.gui.mob_stats.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.lostluma.server_stats.gui.mob_stats.MobStatsListWidget;
import net.lostluma.server_stats.gui.TooltipConsumer;
import net.lostluma.server_stats.gui.mob_stats.button.CallbackButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ListWidget;
import net.minecraft.client.resource.language.I18n;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatsScreen.class)
public class StatsScreenMixin extends Screen implements TooltipConsumer {
	@Unique
	private static final int MOBS_BUTTON_ID = 4;

	@Unique
	MobStatsListWidget mobStats;

	@Shadow
	private ListWidget selectedStatsList;

	@Unique
	@Nullable
	private String[] tooltip = null;


	@Inject(method = "init", at = @At("HEAD"))
	@SuppressWarnings("unchecked")
	public void addMobStatsWidget(CallbackInfo callbackInfo) {
		this.mobStats = new MobStatsListWidget(this.minecraft, this, this.width, this.height, 32, this.height - 64);

		try {
			trySetScrollButtonIds();
		} catch (NoSuchMethodError err) {
		}

		this.mobStats.hideButtons();
		this.mobStats.addButtons(this.buttons::add);
	}

	@Unique
	private void trySetScrollButtonIds() {
		this.mobStats.setScrollButtonIds(this.buttons, 1, 1);
	}

	@Inject(method = "createButtons", at = @At("TAIL"))
	@SuppressWarnings("unchecked")
	public void addMobsButton(CallbackInfo callbackInfo) {
		ButtonWidget mobsButton = new ButtonWidget(MOBS_BUTTON_ID, this.width / 2 + 166, this.height - 52, 100, 20, I18n.translate("server_stats.stats_screen.mobs"));

		this.buttons.add(mobsButton);
	}

	@Inject(method = "buttonClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/StatsScreen;selectedStatsList:Lnet/minecraft/client/gui/widget/ListWidget;", opcode = Opcodes.PUTFIELD))
	public void selectedListChanged(CallbackInfo callbackInfo) {
		this.mobStats.hideButtons();
	}

	@Inject(method = "buttonClicked", at = @At("TAIL"))
	public void mobsButtonClicked(ButtonWidget button, CallbackInfo callbackInfo) {
		if (button.id == MOBS_BUTTON_ID) {
			this.selectedStatsList = this.mobStats;
			this.mobStats.showButtons();
		}
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"))
	public void addButtonCallbacks(ButtonWidget button, CallbackInfo callbackInfo) {
		if (button instanceof CallbackButton) {
			((CallbackButton) button).callback();
		}
	}

	@Inject(method = "render", at = @At("HEAD"))
	public void resetTooltip(CallbackInfo callbackInfo) {
		this.tooltip = null;
	}

	@Inject(method = "render", at = @At("TAIL"))
	public void renderTooltip(CallbackInfo callbackInfo, @Local(ordinal = 0, argsOnly = true) int mouseX, @Local(ordinal = 1, argsOnly = true) int mouseY) {
		if (this.mobStats == this.selectedStatsList) {
			this.mobStats.renderOrderArrow();
		}

		if (this.tooltip == null) {
			return;
		}

		if (this.tooltip.length == 0) {
			return;
		}

		int width = 0;
		int height = 9 * this.tooltip.length;

		for (String line : this.tooltip) {
			int local = this.textRenderer.getStringWidth(line);

			if (local > width) {
				width = local;
			}
		}

		int x;

		// Display tooltip on the right
		// Unless there's no space, then left
		if (this.width - mouseX > width + 6) {
			x = mouseX + 8;
		} else {
			x = mouseX - 8 - width;
		}

		int y = mouseY - 4;

		this.fillGradient(x - 3, y - 3, x + width + 3, y + height + 3, -1073741824, -1073741824);

		for (int idx = 0; idx < tooltip.length; idx++) {
			this.drawString(this.textRenderer, this.tooltip[idx], x, y + 9 * idx, 0xFFFFFF);
		}
	}

	@Override
	public void server_stats$acceptTooltip(String... lines) {
		this.tooltip = lines;
	}
}
