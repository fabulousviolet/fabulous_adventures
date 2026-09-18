package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.datagen.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = FabulousAdventures.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FabulousAdventuresDatagen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        //add providers here vvv
        generator.addProvider(true,new FabulousItemModelProvider(packOutput,event.getExistingFileHelper()));
        generator.addProvider(true, new FabulousBlockStateProvider(packOutput,event.getExistingFileHelper()));
        FabulousBlockTagProvider blockTagProvider = generator.addProvider(true, new FabulousBlockTagProvider(packOutput, lookupProvider,event.getExistingFileHelper()));
        generator.addProvider(true, new FabulousItemTagProvider(packOutput,lookupProvider, blockTagProvider.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(FabulousBlockLoottableProvider::new,
                        LootContextParamSets.BLOCK)),lookupProvider));
        generator.addProvider(true, new FabulousRecipeProvider.Runner(packOutput,lookupProvider));
    }
}
