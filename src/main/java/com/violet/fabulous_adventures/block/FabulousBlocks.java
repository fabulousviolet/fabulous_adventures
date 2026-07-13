package com.violet.fabulous_adventures.block;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class FabulousBlocks {
    //create a deferred register for Blocks
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousAdventures.MODID);

    //register Blocks here vvv dummy: public static final DeferredBlock<Block> NAME = registerBlock("name", properties -> new Block(properties.strength(x,x);
    public static final DeferredBlock<Block> ROPE = registerBlock("rope", properties -> new RopeBlock(properties.strength(0.8f,0.8f)));
    //register functions
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FabulousItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus event_bus) {
        BLOCKS.register(event_bus);
    }
}
