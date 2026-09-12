package com.violet.fabulous_adventures.item.custom.claymore;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.checkerframework.checker.nullness.qual.NonNull;


public enum ClaymoreChargeState implements StringRepresentable {
    NORMAL("normal"),
    CHARGED("charged"),
    RELEASE("release");

    private final String name;

    ClaymoreChargeState(String name) {
        this.name = name;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }

    public static final Codec<ClaymoreChargeState> CODEC =
            StringRepresentable.fromEnum(ClaymoreChargeState::values);
    public static final StreamCodec<ByteBuf, ClaymoreChargeState> STREAM_CODEC = ByteBufCodecs.idMapper(
            i -> ClaymoreChargeState.values()[i],
            ClaymoreChargeState::ordinal
    );

}

