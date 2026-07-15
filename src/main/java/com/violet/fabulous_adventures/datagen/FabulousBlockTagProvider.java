package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FabulousBlockTagProvider extends BlockTagsProvider {
    public FabulousBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FabulousAdventures.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.SHEARS_MAJOR_BREAKING_SPEED)
                .add(FabulousBlocks.ROPE.getKey());
        tag(BlockTags.CLIMBABLE)
                .add(FabulousBlocks.ROPE_CLIMBABLE.getKey());
    }

}