package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class FabulousBlockLoottableProvider extends BlockLootSubProvider {
    public FabulousBlockLoottableProvider( HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(FabulousBlocks.ROPE.get());
        dropSelf(FabulousBlocks.OXYGEN_TANK.get());
        dropSelf(FabulousBlocks.MAP_DISPLAY.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FabulousBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
