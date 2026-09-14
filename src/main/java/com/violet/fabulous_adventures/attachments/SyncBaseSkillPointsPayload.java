package com.violet.fabulous_adventures.attachments;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncBaseSkillPointsPayload(int value) implements CustomPacketPayload {
    public static final Type<SyncBaseSkillPointsPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "sync_base_skill_points"));

    public static final StreamCodec<ByteBuf, SyncBaseSkillPointsPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.VAR_INT, SyncBaseSkillPointsPayload::value, SyncBaseSkillPointsPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
