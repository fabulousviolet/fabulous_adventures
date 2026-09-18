package com.violet.fabulous_adventures.item.custom.claymore;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT, bus =  EventBusSubscriber.Bus.MOD)

public class ClaymoreModelPropertyRegistry {

    public static final ResourceLocation CLAYMORE_STATE =
            ResourceLocation.fromNamespaceAndPath(
                    FabulousAdventures.MODID,
                    "claymore_state"
            );

    @SubscribeEvent
    public static void registerProperties(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {

            ItemProperties.register(
                    FabulousItems.CLAYMORE.get(),
                    CLAYMORE_STATE,
                    (stack, level, entity, seed) -> {

                        ClaymoreChargeState state =
                                stack.getOrDefault(
                                        FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(),
                                        ClaymoreChargeState.NORMAL
                                );

                        return switch (state) {

                            case NORMAL -> 0.0F;

                            case CHARGED -> 1.0F;

                            case RELEASE -> 2.0F;
                        };
                    }
            );
        });
    }
}
