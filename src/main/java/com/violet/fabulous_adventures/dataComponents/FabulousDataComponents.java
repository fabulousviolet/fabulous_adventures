package com.violet.fabulous_adventures.dataComponents;

import com.mojang.serialization.Codec;
import com.violet.fabulous_adventures.FabulousAdventures;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FabulousAdventures.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GLIDER_ACTIVE =
            DATA_COMPONENTS.register("glider_active", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
