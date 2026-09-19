package com.violet.fabulous_adventures.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class FabulousKeybinds {
    public static final KeyMapping.Category FABULOUS_KEY_CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "fabulous_adventures"));

    public static final KeyMapping OPEN_SKILLTREE = new KeyMapping(
            "key.fabulousadventures.open_skilltree",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_K,
            FABULOUS_KEY_CATEGORY
    );
    public static final KeyMapping CRAWL_SKILL_KEYBIND = new KeyMapping(
            "key.fabulousadventures.crawl_skill_keybind",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_LALT,
            FABULOUS_KEY_CATEGORY
    );
}