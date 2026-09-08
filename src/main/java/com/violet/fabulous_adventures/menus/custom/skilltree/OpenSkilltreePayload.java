package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenSkilltreePayload() implements CustomPacketPayload {
    public static final Type<OpenSkilltreePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "open_skilltree"));

    public static final StreamCodec<ByteBuf, OpenSkilltreePayload> STREAM_CODEC =
            StreamCodec.unit(new OpenSkilltreePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}