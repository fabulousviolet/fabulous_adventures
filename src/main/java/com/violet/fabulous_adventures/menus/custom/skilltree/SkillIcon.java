package com.violet.fabulous_adventures.menus.custom.skilltree;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public sealed interface SkillIcon permits SkillIcon.Item, SkillIcon.Texture {
    record Item(ItemStack stack) implements SkillIcon {}
    record Texture(ResourceLocation location) implements SkillIcon {}

    default void render(GuiGraphics graphics, int x, int y) {
        switch (this) {
            case Item item -> graphics.renderItem(item.stack(), x, y);
            case Texture texture -> graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture.location(), x, y, 16, 16);
        }
    }
}