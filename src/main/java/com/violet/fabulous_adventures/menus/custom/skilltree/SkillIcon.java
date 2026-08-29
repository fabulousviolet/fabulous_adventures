package com.violet.fabulous_adventures.menus.custom.skilltree;


import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public sealed interface SkillIcon permits SkillIcon.Item, SkillIcon.Texture {
    record Item(ItemStack stack) implements SkillIcon {}
    record Texture(Identifier location) implements SkillIcon {}

    default void render(GuiGraphicsExtractor graphics, int x, int y) {
        switch (this) {
            case Item item -> graphics.item(item.stack(), x, y);
            case Texture texture -> graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture.location(), x, y, 16, 16);
        }
    }
}