package com.violet.fabulous_adventures.core;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)

public class FabulousKeybindRegistry {
    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(FabulousKeybinds.OPEN_SKILLTREE);
        event.register(FabulousKeybinds.CRAWL_SKILL_KEYBIND);
    }
}