package com.violet.fabulous_adventures.CreativeModeTabs;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FabulousCreativeModeTabs {
    //create deferred register for creative mode tabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FabulousAdventures.MODID);

    //register tabs here vvv
    public static Supplier<CreativeModeTab> FABULOUS_ADVENTURES_TAB = CREATIVE_MODE_TABS.register("fabulous_adventures_tab", () ->CreativeModeTab.builder().icon(() -> new ItemStack(FabulousBlocks.ROPE.get()))
            .title(Component.translatable("creativetab.fabulousadventures.fabulous_adventures_tab"))
            .displayItems((a, output) -> {

                output.accept(FabulousBlocks.ROPE);
                output.accept(FabulousItems.ROPE_ARROW);
                output.accept(FabulousItems.GLIDER);
                output.accept(FabulousItems.MACHETE);
                output.accept(FabulousItems.CLAYMORE);
                output.accept(FabulousItems.EMPTY_ADVANCED_MAP);
                output.accept(FabulousItems.ADVANCED_MAP);
                output.accept(FabulousBlocks.MAP_DISPLAY);
                output.accept(FabulousBlocks.OXYGEN_TANK);

            })
            .build());






    //register function
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}


