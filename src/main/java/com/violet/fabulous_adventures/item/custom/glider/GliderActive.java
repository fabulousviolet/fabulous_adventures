package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

//a data component to check whether the entity is gliding
@EventBusSubscriber(
        modid = FabulousAdventures.MODID,
        value = Dist.CLIENT)
public class GliderActive{
    public static final ResourceLocation PROPERTY =
            ResourceLocation.fromNamespaceAndPath(
                    FabulousAdventures.MODID,
                    "glider_active"
            );

    @SubscribeEvent
    public static void registerProperties(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {

            ItemProperties.register(
                    FabulousItems.GLIDER.get(),
                    PROPERTY,
                    (ItemStack stack, net.minecraft.client.multiplayer.ClientLevel level,
                     net.minecraft.world.entity.LivingEntity entity, int seed) -> {

                        return stack.getOrDefault(
                                FabulousDataComponents.GLIDER_ACTIVE.get(),
                                false
                        ) ? 1.0F : 0.0F;
                    }
            );
        });
    }

}
