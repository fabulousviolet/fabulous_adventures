package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SkilltreeButtonPayload(String nodeId,int cost, String parent) implements CustomPacketPayload {

    public static final Type<SkilltreeButtonPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "skill_button"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static final StreamCodec<ByteBuf, SkilltreeButtonPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SkilltreeButtonPayload::nodeId,
            ByteBufCodecs.INT, SkilltreeButtonPayload::cost,
            ByteBufCodecs.STRING_UTF8,SkilltreeButtonPayload::parent,
            SkilltreeButtonPayload::new
    );
}
