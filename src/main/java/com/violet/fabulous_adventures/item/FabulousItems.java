package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.EmptyAdvancedMapItem;
import com.violet.fabulous_adventures.item.custom.MacheteItem;
import com.violet.fabulous_adventures.item.custom.RopeArrowItem;
import com.violet.fabulous_adventures.item.custom.advanced_map.AdvancedMapItem;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreItem;
import com.violet.fabulous_adventures.item.custom.glider.GliderItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class FabulousItems {
    //create a deferred register for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousAdventures.MODID);

    //register items here vvv
    public static final DeferredItem<ArrowItem> ROPE_ARROW = ITEMS.registerItem("rope_arrow", properties -> new RopeArrowItem(properties){
        @Override
        public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            components.add(Component.literal("Shoot it at a ceiling to let down a rope you can climb up."));
            super.appendHoverText(itemStack, context, components, tooltipFlag);
        }
    });
    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", properties ->
            new GliderItem(properties.stacksTo(1)
                    .durability(1000)
                    //TODO: add repairable
            ));
    public static final DeferredItem<Item> MACHETE = ITEMS.registerItem("machete", properties -> new MacheteItem(properties
            .attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"machete_strength"),4.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"machete_attack_speed"),-2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                    .build())
            .durability(512)
            //TODO: add repairable
            .stacksTo(1)

    ));
    public static final DeferredItem<Item> CLAYMORE = ITEMS.registerItem("claymore",properties -> new ClaymoreItem(
                    properties
                            .attributes(ItemAttributeModifiers.builder()
                                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"claymore_strength"),7.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"claymore_attack_speed"),-3.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                                    .build())
                            .durability(1024)
                            //TODO: add repairable
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
