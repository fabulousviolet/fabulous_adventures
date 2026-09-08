package com.violet.fabulous_adventures.item.custom.advanced_map;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.core.FabulousMapDecorationTypes;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Map;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class AdvancedMapLoginHandler {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        Level level = player.level();

        restoreWaypointsInInventory(level, player.getInventory());
    }

    private static void restoreWaypointsInInventory(Level level, Inventory inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!(stack.getItem() instanceof AdvancedMapItem)) continue;

            MapItemSavedData savedData = AdvancedMapItem.getSavedData(stack, level);
            if (savedData == null) continue;

            Map<String, BlockPos> waypoints = stack.getOrDefault(FabulousDataComponents.WAYPOINT_POSITIONS.get(), Map.of());
            for (var entry : waypoints.entrySet()) {
                BlockPos pos = entry.getValue();
                savedData.addDecoration(FabulousMapDecorationTypes.WAYPOINT.getDelegate(), level, entry.getKey(),
                        pos.getX(), pos.getZ(), 180.0, null);
            }
        }
    }
}
