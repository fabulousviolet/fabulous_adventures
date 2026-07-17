package com.violet.fabulous_adventures;

import com.violet.fabulous_adventures.datagen.FabulousBlockTagProvider;
import com.violet.fabulous_adventures.datagen.FabulousItemTagProvider;
import com.violet.fabulous_adventures.datagen.FabulousModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class FabulousAdventuresDatagen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new FabulousModelProvider(packOutput));
        generator.addProvider(true, new FabulousBlockTagProvider(packOutput, lookupProvider));
        generator.addProvider(true, new FabulousItemTagProvider(packOutput,lookupProvider));
        /*generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLoottableProvider::new,
                        LootContextParamSets.BLOCK)),lookupProvider));
        generator.addProvider(true, new ModRecipeProvider.Runner(packOutput,lookupProvider));*/
    }
}
