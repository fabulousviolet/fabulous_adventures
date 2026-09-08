package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.block_entity.FabulousBlockEntities;
import com.violet.fabulous_adventures.block_entity.custom.MapDisplay.MapDisplayBlockEntityRenderer;
import com.violet.fabulous_adventures.block_entity.custom.OxygenTank.OxygenTankBlockEntityRenderer;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.entity.FabulousEntities;
import com.violet.fabulous_adventures.entityRenderer.RopeArrowRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
@EventBusSubscriber(modid = FabulousAdventures.MODID,value = Dist.CLIENT)
public class FabulousEntityRendererRegistry{
    //register the entity renderers
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FabulousEntities.ROPE_ARROW.get(), RopeArrowRenderer::new);
        event.registerBlockEntityRenderer(FabulousBlockEntities.MAP_DISPLAY.get(), MapDisplayBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(FabulousBlockEntities.OXYGEN_TANK.get(), OxygenTankBlockEntityRenderer::new);
    }
}

