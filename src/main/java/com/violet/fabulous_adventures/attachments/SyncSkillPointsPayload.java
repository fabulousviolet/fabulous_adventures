package com.violet.fabulous_adventures.attachments;

import com.mojang.datafixers.types.Type;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncSkillPointsPayload(int value) implements CustomPacketPayload {
    public static final Type<SyncSkillPointsPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "sync_skill_points"));

    public static final StreamCodec<ByteBuf, SyncSkillPointsPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.VAR_INT, SyncSkillPointsPayload::value, SyncSkillPointsPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
