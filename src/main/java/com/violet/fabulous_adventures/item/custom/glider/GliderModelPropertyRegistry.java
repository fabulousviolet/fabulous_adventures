package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousAdventures;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
//registers the fact the glider changes model depending on a condition
@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class GliderModelPropertyRegistry {
    @SubscribeEvent
    public static void registerConditionalProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(
                Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "glider_active"),
                GliderActive.MAP_CODEC
        );
    }
}
