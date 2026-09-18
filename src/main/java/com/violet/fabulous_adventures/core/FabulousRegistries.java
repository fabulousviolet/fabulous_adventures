package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.skills.FabulousSkills;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID)

public class FabulousRegistries {
    @SubscribeEvent
    public static void newRegistries(NewRegistryEvent event) {
        event.register(FabulousSkills.SKILL_REGISTRY);
    }
}