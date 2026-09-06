package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.advanced_map.AdvancedMapItem;
import com.violet.fabulous_adventures.item.custom.EmptyAdvancedMapItem;
import com.violet.fabulous_adventures.item.custom.MacheteItem;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreItem;
import com.violet.fabulous_adventures.item.custom.glider.GliderItem;
import com.violet.fabulous_adventures.item.custom.RopeArrowItem;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class FabulousItems {
    //create a deferred register for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousAdventures.MODID);

    //register items here vvv
    public static final DeferredItem<ArrowItem> ROPE_ARROW = ITEMS.registerItem("rope_arrow", properties -> new RopeArrowItem(properties){
        @Override
        public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
            builder.accept(Component.literal("Shoot it at a ceiling to let down a rope you can climb up."));
            super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        }
    });
    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", properties ->
            new GliderItem(properties.stacksTo(1)
                    .durability(1000)
                    .repairable(ItemTags.WOOL)
            ));
    public static final DeferredItem<Item> MACHETE = ITEMS.registerItem("machete", properties -> new MacheteItem(properties
            .sword(ToolMaterial.IRON,4.0f,-2.0f)
            .repairable(Items.IRON_INGOT)
            .durability(512)
            .stacksTo(1)

    ));
    public static final DeferredItem<Item> CLAYMORE = ITEMS.registerItem("claymore",properties -> new ClaymoreItem(
                    properties.sword(ToolMaterial.NETHERITE,7.0f,-3.0f)
                            .durability(1024)
                            .repairable(Items.ANCIENT_DEBRIS)
                            .stacksTo(1)
            )
    );
    public static final  DeferredItem<EmptyAdvancedMapItem> EMPTY_ADVANCED_MAP = ITEMS.registerItem("empty_advanced_map", properties ->
            new EmptyAdvancedMapItem(properties.stacksTo(1)));
    public static final  DeferredItem<AdvancedMapItem> ADVANCED_MAP = ITEMS.registerItem("advanced_map", properties ->
            new AdvancedMapItem(properties.stacksTo(1)));
    //register function
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
