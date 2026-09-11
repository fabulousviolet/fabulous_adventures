package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.menus.custom.skilltree.OpenSkilltreePayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class SkillpointProgressScreen extends AbstractContainerScreen<SkillpointProgressMenu> {

    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/gui/skillpoint_progress_bg.png");
    private static final int CONTENT_INSET = 9;
    private final List<SkillpointProgressEntryWidget> entryWidgets = new ArrayList<>();
    private static final int ENTRY_HEIGHT = 24;
    private static final int SCROLL_PIXELS_PER_NOTCH = 12;
    public SkillpointProgressScreen(SkillpointProgressMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title,256,194);
    }

    private List<SkillpointProgressEntryWidget> createEntryWidgets() {
        List<SkillpointProgressEntryWidget> widgets = new ArrayList<>();
        int startY = topPos + CONTENT_INSET + 20;

        for (SkillpointProgressEntry entry : SkillpointProgressEntries.ENTRIES) {
            int y = startY + entry.index() * ENTRY_HEIGHT;
            widgets.add(new SkillpointProgressEntryWidget(
                    leftPos + CONTENT_INSET, y, imageWidth - CONTENT_INSET * 2, ENTRY_HEIGHT - 2, entry
            ));
        }
        return widgets;
    }
    private double scrollOffset;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,
                                 double scrollX, double scrollY) {
        if (mouseX < leftPos || mouseX >= leftPos + imageWidth
                || mouseY < topPos || mouseY >= topPos + imageHeight) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        scrollOffset = Mth.clamp(
                scrollOffset - scrollY * SCROLL_PIXELS_PER_NOTCH,
                0.0,
                getMaxScrollOffset()
        );
        updateEntryPositions();
        return true;
    }

    private double getMaxScrollOffset() {
        int listHeight = SkillpointProgressEntries.ENTRIES.size() * ENTRY_HEIGHT;
        int visibleHeight = imageHeight - (CONTENT_INSET * 2) - 20;
        return Math.max(0, listHeight - visibleHeight);
    }

    private void updateEntryPositions(){
        for(SkillpointProgressEntryWidget widget : entryWidgets){
            int x = leftPos + CONTENT_INSET;
            int y = topPos + CONTENT_INSET + 20 + widget.entry.index() * ENTRY_HEIGHT - (int) scrollOffset;
            widget.setPosition(x, y);
        }
    }
    @Override
    protected void init() {
        super.init();
        entryWidgets.clear();
        entryWidgets.addAll(createEntryWidgets());
        for (SkillpointProgressEntryWidget widget : entryWidgets) {
            this.addRenderableWidget(widget);
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 194);
        graphics.text(this.font, Component.literal("Skillpoint Progress"), leftPos + 9, topPos + 6, -12566464, false);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int contentX0 = leftPos + CONTENT_INSET;
        int contentY0 = topPos + CONTENT_INSET + 8;
        int contentX1 = leftPos + imageWidth - CONTENT_INSET;
        int contentY1 = topPos + imageHeight - CONTENT_INSET;

        graphics.enableScissor(contentX0, contentY0, contentX1, contentY1);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public void onClose() {
        ClientPacketDistributor.sendToServer(new OpenSkilltreePayload());
        super.onClose();
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }
}
