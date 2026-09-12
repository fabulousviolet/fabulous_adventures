package com.violet.fabulous_adventures.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

public class FabulousKeybinds {
    private static final String FABULOUS_KEY_CATEGORY = "FabulousAdventures";
    public static final KeyMapping OPEN_SKILLTREE = new KeyMapping(
            "key.fabulousadventures.open_skilltree",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_K,
            FABULOUS_KEY_CATEGORY
    );
    public static final KeyMapping CRAWL_SKILL_KEYBIND = new KeyMapping(
            "key.fabulousadventures.crawl_skill_keybind",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LALT,
            FABULOUS_KEY_CATEGORY
    );
}