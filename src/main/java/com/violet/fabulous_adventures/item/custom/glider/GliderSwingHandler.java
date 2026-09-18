package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)

public class GliderSwingHandler {
    @SubscribeEvent
    public static void onInteractionKeyTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;

        Player player = Minecraft.getInstance().player;
        if (player != null && player.getItemInHand(event.getHand()).getItem() instanceof GliderItem) {
            event.setSwingHand(false);
        }
    }
}
