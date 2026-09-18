package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.core.FabulousKeybinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;


@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)

public class SkilltreeKeyHandler {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (FabulousKeybinds.OPEN_SKILLTREE.consumeClick()) {
            PacketDistributor.sendToServer(new OpenSkilltreePayload());
        }
    }
}