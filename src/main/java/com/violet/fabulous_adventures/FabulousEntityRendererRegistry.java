package com.violet.fabulous_adventures;

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
    }
}

