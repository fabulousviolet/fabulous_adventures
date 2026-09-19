package com.violet.fabulous_adventures.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousMapDecorationTypes {

    public static final DeferredRegister<MapDecorationType> MAP_DECORATION_TYPES =
            DeferredRegister.create(Registries.MAP_DECORATION_TYPE, FabulousAdventures.MODID);

    public static final DeferredHolder<MapDecorationType, MapDecorationType> WAYPOINT = register("waypoint", "waypoint", true, true);

    private static DeferredHolder<MapDecorationType, MapDecorationType> register(String name, String assetName, boolean showOnItemFrame, boolean trackCount) {
        return register(name, assetName, showOnItemFrame, -1, trackCount, false);
    }

    private static DeferredHolder<MapDecorationType, MapDecorationType> register(
            String name, String assetName, boolean showOnItemFrame, int mapColor, boolean trackCount, boolean explorationMapElement
    ) {
        return MAP_DECORATION_TYPES.register(name, () -> new MapDecorationType(
                Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, assetName),
                showOnItemFrame, trackCount
        ));
    }

    public static void register(IEventBus eventBus) {
        MAP_DECORATION_TYPES.register(eventBus);
    }
}