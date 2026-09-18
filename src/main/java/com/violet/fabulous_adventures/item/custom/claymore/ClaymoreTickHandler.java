package com.violet.fabulous_adventures.item.custom.claymore;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, bus = EventBusSubscriber.Bus.GAME)


public class ClaymoreTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!(stack.getItem() instanceof ClaymoreItem)) continue;

            ClaymoreChargeState state = stack.getOrDefault(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
            if (state != ClaymoreChargeState.RELEASE) continue;

            int ticksLeft = stack.getOrDefault(FabulousDataComponents.CLAYMORE_RELEASE_TICKS.get(), 0);
            if (ticksLeft <= 1) {
                stack.set(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
                stack.remove(FabulousDataComponents.CLAYMORE_RELEASE_TICKS.get());
            } else {
                stack.set(FabulousDataComponents.CLAYMORE_RELEASE_TICKS.get(), ticksLeft - 1);
            }
        }
    }
}
