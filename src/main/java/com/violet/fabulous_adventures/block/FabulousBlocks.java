package com.violet.fabulous_adventures.block;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.block.custom.RopeBuilder;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class FabulousBlocks {
    //create a deferred register for Blocks
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousAdventures.MODID);

    //register Blocks here vvv dummy: public static final DeferredBlock<Block> NAME = registerBlock("name", properties -> new Block(properties.strength(x,x);
    public static final DeferredBlock<Block> ROPE = registerBlock("rope", properties -> new RopeBlock(properties.strength(0.8f,0.8f).sound(SoundType.WOOL),false),true);
    public static final DeferredBlock<Block> ROPE_CLIMBABLE = registerBlock("rope_climbable", properties -> new RopeBlock(properties.strength(0.8f,0.8f).sound(SoundType.WOOL),true),false);
    public static final DeferredBlock<Block> ROPE_BUILDER = registerBlock("rope_builder", RopeBuilder::new,true);
    //register functions
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, boolean register_item) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        if(register_item) registerBlockItem(name, toReturn);
        return toReturn;

    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FabulousItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }


    public static void register(IEventBus event_bus) {
        BLOCKS.register(event_bus);
    }
}
