package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class FabulousKeybindRegistry {
    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.registerCategory(FabulousKeybinds.FABULOUS_KEY_CATEGORY);
        event.register(FabulousKeybinds.OPEN_SKILLTREE);
        event.register(FabulousKeybinds.CRAWL_SKILL_KEYBIND);
    }
}