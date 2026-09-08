package com.violet.fabulous_adventures.dataComponents;

import com.mojang.serialization.Codec;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreChargeState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class FabulousDataComponents {
    //create a deferred register for Data Components
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FabulousAdventures.MODID);
    //define Data Components here vvv
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GLIDER_ACTIVE =
            DATA_COMPONENTS.register("glider_active", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ClaymoreChargeState>> CLAYMORE_CHARGE_STATE =
            DATA_COMPONENTS.register("claymore_charge_state", () -> DataComponentType.<ClaymoreChargeState>builder()
                    .persistent(ClaymoreChargeState.CODEC)
                    .networkSynchronized(ClaymoreChargeState.STREAM_CODEC)
                    .build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CLAYMORE_RELEASE_TICKS =
            DATA_COMPONENTS.register("claymore_release_ticks", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Map<String, BlockPos>>> WAYPOINT_POSITIONS =
            DATA_COMPONENTS.register("waypoint_positions", () -> DataComponentType.<Map<String, BlockPos>>builder()
                    .persistent(Codec.unboundedMap(Codec.STRING, BlockPos.CODEC))
                    .networkSynchronized(ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, BlockPos.STREAM_CODEC))
                    .build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> OXYGEN_TANK_VALUE =
            DATA_COMPONENTS.register("oxygen_tank_values", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}