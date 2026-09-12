package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Set;

public class SkillpointProgressEntryWidget extends AbstractWidget {
    public final SkillpointProgressEntry entry;
    private static final Identifier PROGRESS_BAR_TEXT_LOC = Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"progress_bar");
    private static final Identifier PROGRESS_BAR_FILLED_TEXT_LOC = Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"progress_bar_filled");
    public SkillpointProgressEntryWidget(int x, int y, int width, int height, SkillpointProgressEntry entry) {
        super(x, y, width, height, entry.label());
        this.entry = entry;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        double weight = SkillProgressCalculator.getWeightFor(entry.type(), entry.id());
        double rawValue = SkillProgressCalculator.getRawValue(player, entry.type(), entry.id());
        Set<Integer> milestones = SkillProgressCalculator.getMilestonesFor(entry.type());
        MilestoneProgress progress = SkillProgressCalculator.computeProgress(rawValue, milestones, weight);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, entry.imageLoc(), getX(), getY()-8, 16, 16);

        int barX = getX() + 20;
        int barY = getY() + 4;
        int barWidth = getWidth() - 24;
        int barHeight = 5;

        int filledWidth = (int) (barWidth * progress.fillFraction());
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_BAR_TEXT_LOC,barX,barY,barWidth,barHeight);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_BAR_FILLED_TEXT_LOC,barX,barY,filledWidth,barHeight);
        graphics.drawString(Minecraft.getInstance().font, entry.label(), barX, getY() - 10, -1, false);

        setTooltip(Tooltip.create(Component.literal(
                (int) progress.current() + " / " + (int) progress.nextMilestone()
        )));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, entry.label());
    }
}
