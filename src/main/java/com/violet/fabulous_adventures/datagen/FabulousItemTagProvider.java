package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabulousItemTagProvider extends ItemTagsProvider {
    public FabulousItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, FabulousAdventures.MODID, existingFileHelper);
    }
    //define custom itemTags here vvv

    
    //add items to ItemTags here
    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(ItemTags.ARROWS)
                .add(FabulousItems.ROPE_ARROW.asItem());

    }
}
