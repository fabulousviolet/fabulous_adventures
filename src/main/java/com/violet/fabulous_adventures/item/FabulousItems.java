package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.FabulousAdventures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousItems {
//create a deferred register for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousAdventures.MODID);

//register items here vvv

//register function
public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
}


}
