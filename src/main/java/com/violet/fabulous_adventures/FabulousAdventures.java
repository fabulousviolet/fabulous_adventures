package com.violet.fabulous_adventures;

import com.violet.fabulous_adventures.CreativeModeTabs.FabulousCreativeModeTabs;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.entity.FabulousEntities;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FabulousAdventures.MODID)
public class FabulousAdventures {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fabulousadventures";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FabulousAdventures(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        //call register methods
        FabulousCreativeModeTabs.register(modEventBus);
        FabulousBlocks.register(modEventBus);
        FabulousItems.register(modEventBus);
        FabulousEntities.register(modEventBus);
        FabulousDataComponents.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == FabulousCreativeModeTabs.FABOULOUS_ADVENTURES_TAB){
            event.accept(FabulousBlocks.ROPE);
            event.accept(FabulousBlocks.ROPE_BUILDER);
            event.accept(FabulousItems.ROPE_ARROW);
            event.accept(FabulousItems.GLIDER);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
