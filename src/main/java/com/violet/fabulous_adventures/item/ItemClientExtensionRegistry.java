package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreItemClientExtensions;
import com.violet.fabulous_adventures.item.custom.glider.GliderClientItemExtensions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
//registers the GliderItem to the event bus
@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class ItemClientExtensionRegistry {
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new GliderClientItemExtensions(), FabulousItems.GLIDER.get());
        event.registerItem(new ClaymoreItemClientExtensions(), FabulousItems.CLAYMORE.get());
    }
}