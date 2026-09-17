package com.violet.fabulous_adventures.block_entity;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block_entity.custom.MapDisplay.MapDisplay;
import com.violet.fabulous_adventures.block_entity.custom.OxygenTank.OxygenTank;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FabulousAdventures.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MapDisplay>> MAP_DISPLAY = BLOCK_ENTITY_TYPES.register(
            "map_display",
            () -> new BlockEntityType<>(MapDisplay::new,  FabulousBlocks.MAP_DISPLAY.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OxygenTank>> OXYGEN_TANK = BLOCK_ENTITY_TYPES.register(
                "oxygen_tank",
                () -> new BlockEntityType<>(OxygenTank::new,  FabulousBlocks.OXYGEN_TANK.get())
        );

    public static void register(IEventBus event_bus) {
        BLOCK_ENTITY_TYPES.register(event_bus);
    }
}


