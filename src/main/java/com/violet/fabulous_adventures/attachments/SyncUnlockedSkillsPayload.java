package com.violet.fabulous_adventures.attachments;

import com.mojang.datafixers.types.Type;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public record SyncUnlockedSkillsPayload(Set<String> value) implements CustomPacketPayload {
    public static final Type<SyncUnlockedSkillsPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "sync_unlocked_skills"));

    public static final StreamCodec<ByteBuf, SyncUnlockedSkillsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(HashSet::new, ByteBufCodecs.STRING_UTF8), SyncUnlockedSkillsPayload::value,
            SyncUnlockedSkillsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
