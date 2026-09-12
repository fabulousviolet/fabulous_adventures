package com.violet.fabulous_adventures.item.custom.claymore;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class ClaymoreModelPropertyRegistry {
    @SubscribeEvent
    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "claymore_charge_state"),
                ClaymoreState.TYPE
        );
    }
}
