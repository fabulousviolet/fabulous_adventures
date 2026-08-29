package com.violet.fabulous_adventures.menus.custom.skilltree;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;

public record SkillnodeDef(String id, String parentId, int cost, int x, int y, SkillIcon icon, Component tooltip) {
}
