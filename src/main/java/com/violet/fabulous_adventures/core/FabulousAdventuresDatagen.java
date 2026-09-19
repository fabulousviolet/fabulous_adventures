package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.datagen.*;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class FabulousAdventuresDatagen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getReloadableLookupProvider();
        //add providers here vvv
        generator.addProvider(true, new FabulousModelProvider(packOutput));
        generator.addProvider(true, new FabulousBlockTagProvider(packOutput, lookupProvider));
        generator.addProvider(true, new FabulousItemTagProvider(packOutput,lookupProvider));
        event.createReloadableRegistryObjects(
                new RegistrySetBuilder()
                        .add(RecipeProvider.asBootstrap(FabulousRecipeProvider::new))
                        .add(Registries.LOOT_TABLE,FabulousBlockLoottableProvider.create())
        );
    }
}
