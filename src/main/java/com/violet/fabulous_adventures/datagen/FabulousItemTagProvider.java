package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabulousItemTagProvider extends ItemTagsProvider {
    //define custom itemTags here vvv

    public FabulousItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FabulousAdventures.MODID);
    }
    //add items to ItemTags here
    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(ItemTags.ARROWS)
                .add(FabulousItems.ROPE_ARROW.asItem());

    }
}
