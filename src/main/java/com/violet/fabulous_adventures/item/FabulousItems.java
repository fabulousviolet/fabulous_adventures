package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.glider.GliderItem;
import com.violet.fabulous_adventures.item.custom.RopeArrowItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousItems {
//create a deferred register for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousAdventures.MODID);

//register items here vvv
    public static final DeferredItem<ArrowItem> ROPE_ARROW = ITEMS.registerItem("rope_arrow", RopeArrowItem::new);
    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", GliderItem::new);
//register function
public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
}


}
