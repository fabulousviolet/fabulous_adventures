package com.violet.fabulous_adventures.entity;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.entity.custom.RopeArrow;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FabulousEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(FabulousAdventures.MODID);

    public static final Supplier<EntityType<RopeArrow>> ROPE_ARROW = ENTITIES.register("rope_arrow"
            ,() -> EntityType.Builder.<RopeArrow>of(
                    RopeArrow::new,
                    MobCategory.MISC
            )
                    .sized(0.5f,0.5f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE,
                            Identifier.fromNamespaceAndPath("fabulousadventures","rope_arrow")))

    );

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
