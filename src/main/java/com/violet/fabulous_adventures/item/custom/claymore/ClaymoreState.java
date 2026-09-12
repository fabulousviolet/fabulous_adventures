package com.violet.fabulous_adventures.item.custom.claymore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;

public record ClaymoreState() implements SelectItemModelProperty<ClaymoreChargeState> {
    public static final MapCodec<ClaymoreState> MAP_CODEC = MapCodec.unit(new ClaymoreState());
    public static final SelectItemModelProperty.Type<ClaymoreState, ClaymoreChargeState> TYPE =
            SelectItemModelProperty.Type.create(MAP_CODEC, ClaymoreChargeState.CODEC);

    @Override
    public ClaymoreChargeState get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, @NonNull ItemDisplayContext context) {
        return stack.getOrDefault(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
    }

    @Override
    public @NonNull Codec<ClaymoreChargeState> valueCodec() {
        return ClaymoreChargeState.CODEC;
    }

    @Override
    public SelectItemModelProperty.@NonNull Type<ClaymoreState, ClaymoreChargeState> type() {
        return TYPE;
    }
}
