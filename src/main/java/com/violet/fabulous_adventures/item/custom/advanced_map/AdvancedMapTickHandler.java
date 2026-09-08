package com.violet.fabulous_adventures.item.custom.advanced_map;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class AdvancedMapTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if(!SkillUtils.isUnlocked(player,"advanced_map_waypoints_plus")) return;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

        ItemStack activeMap = mainHand.getItem() instanceof AdvancedMapItem ? mainHand
                : offHand.getItem() instanceof AdvancedMapItem ? offHand
                  : null;

        if (activeMap == null) return;

        Map<String, BlockPos> waypoints = activeMap.getOrDefault(FabulousDataComponents.WAYPOINT_POSITIONS.get(), Map.of());

        for (BlockPos pos : waypoints.values()) {
            for (int i = 0; i < 50; i++) {
                level.addAlwaysVisibleParticle(ParticleTypes.ELECTRIC_SPARK,
                        pos.getX() + 0.5, pos.getY() + i, pos.getZ() + 0.5,
                        0.0, 0.0, 0.0);
            }
        }
    }
}
