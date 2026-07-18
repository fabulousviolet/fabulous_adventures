package com.violet.fabulous_adventures.item;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.item.custom.MacheteItem;
import com.violet.fabulous_adventures.item.custom.glider.GliderItem;
import com.violet.fabulous_adventures.item.custom.RopeArrowItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FabulousItems {
//create a deferred register for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousAdventures.MODID);

//register items here vvv
    public static final DeferredItem<ArrowItem> ROPE_ARROW = ITEMS.registerItem("rope_arrow", RopeArrowItem::new);
    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", GliderItem::new);
    public static final DeferredItem<Item> MACHETE = ITEMS.registerItem("machete", properties -> new MacheteItem(properties.attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "weapon.attack_damage"), 5.0, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED,
                            new AttributeModifier(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "weapon.attack_speed"), -2.0, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)

                    .build()
    )));
//register function
public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
}


}
