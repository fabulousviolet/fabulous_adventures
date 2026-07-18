package com.violet.fabulous_adventures.item.custom.glider;

import com.mojang.serialization.MapCodec;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
//a data component to check whether the entity is gliding
public record GliderActive() implements ConditionalItemModelProperty {
    public static final MapCodec<GliderActive> MAP_CODEC = MapCodec.unit(new GliderActive());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, @NonNull ItemDisplayContext context) {
        return stack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
    }

    @Override
    public @NonNull MapCodec<GliderActive> type() {
        return MAP_CODEC;
    }
}
