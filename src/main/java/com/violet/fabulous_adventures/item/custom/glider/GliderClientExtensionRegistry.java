package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
//registers the GliderItem to the event bus
@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class GliderClientExtensionRegistry {
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new GliderClientItemExtensions(), FabulousItems.GLIDER.get());
    }
}