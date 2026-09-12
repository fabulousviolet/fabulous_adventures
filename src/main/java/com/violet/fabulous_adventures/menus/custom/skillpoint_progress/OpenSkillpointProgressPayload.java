package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenSkillpointProgressPayload() implements CustomPacketPayload {
    public static final Type<com.violet.fabulous_adventures.menus.custom.skillpoint_progress.OpenSkillpointProgressPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "open_skillpoint_progress"));

    public static final StreamCodec<ByteBuf, com.violet.fabulous_adventures.menus.custom.skillpoint_progress.OpenSkillpointProgressPayload> STREAM_CODEC =
            StreamCodec.unit(new com.violet.fabulous_adventures.menus.custom.skillpoint_progress.OpenSkillpointProgressPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
