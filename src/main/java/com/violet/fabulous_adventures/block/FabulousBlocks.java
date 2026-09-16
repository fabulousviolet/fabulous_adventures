package com.violet.fabulous_adventures.block;

import com.violet.fabulous_adventures.block.custom.OxygenTankBlock;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.block.custom.RopeBuilder;
import com.violet.fabulous_adventures.block.custom.map_display.MapDisplayBlock;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

public class FabulousBlocks {
    //create a deferred register for Blocks
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousAdventures.MODID);

    //register Blocks here vvv dummy: public static final DeferredBlock<Block> NAME = registerBlock("name", properties -> new Block(properties.strength(x,x);
    public static final DeferredBlock<Block> ROPE = registerBlock("rope", properties -> new RopeBlock(properties.strength(0.8f,0.8f).sound(SoundType.WOOL),false),true);
    public static final DeferredBlock<Block> ROPE_CLIMBABLE = registerBlock("rope_climbable", properties -> new RopeBlock(properties.strength(0.8f,0.8f).sound(SoundType.WOOL).noLootTable(),true),false);
    public static final DeferredBlock<Block> ROPE_BUILDER = registerBlock("rope_builder", properties -> new RopeBuilder(properties.noLootTable()),true);
    public static final DeferredBlock<Block> MAP_DISPLAY = registerBlock("map_display", properties -> new MapDisplayBlock(properties.strength(1f).sound(SoundType.METAL).destroyTime(10f).requiresCorrectToolForDrops()),true,Component.literal("Lets you display Advanced Maps.\nPlace multiple in a square to scale the display."));
    public static final DeferredBlock<Block> OXYGEN_TANK = registerBlock("oxygen_tank", properties -> new OxygenTankBlock(properties.strength(1f).sound(SoundType.METAL).destroyTime(10f).requiresCorrectToolForDrops()),true,Component.literal("Provides oxygen under water when standing next to it.\nCharge it with air by placing it above water."));
    //register functions
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, boolean register_item) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        if(register_item) registerBlockItem(name, toReturn);
        return toReturn;

    }
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, boolean register_item, Component... components) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        if(register_item) registerBlockItem(name, toReturn, components);
        return toReturn;

    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FabulousItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Component... components) {
        FabulousItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()){
            @Override
            public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> componentList, TooltipFlag tooltipFlag) {
                for(var component : components) {
                    componentList.add(component);
                }
                super.appendHoverText(itemStack, context, componentList, tooltipFlag);
            }

        });
    }


    public static void register(IEventBus event_bus) {
        BLOCKS.register(event_bus);
    }
}
