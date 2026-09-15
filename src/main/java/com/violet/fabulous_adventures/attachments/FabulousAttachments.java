package com.violet.fabulous_adventures.attachments;

import com.mojang.serialization.Codec;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;

public class FabulousAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FabulousAdventures.MODID);

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Integer>> SKILL_POINTS = ATTACHMENT_TYPES.register(
            "skill_points",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("value"))
                    .copyOnDeath()
                    .build()
    );
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Integer>> BASE_SKILL_POINTS = ATTACHMENT_TYPES.register(
            "base_skill_points",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("value"))
                    .copyOnDeath()
                    .build()
    );
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Set<String>>> UNLOCKED_SKILLS = ATTACHMENT_TYPES.register("unlocked_skills",
            () -> {
                Codec<Set<String>> setCodec = Codec.STRING.listOf().xmap(
                        HashSet::new,
                        ArrayList::new
                );

                return AttachmentType.builder(() -> {
                            Set<String> defaultUnlocked = new HashSet<>();
                            defaultUnlocked.add("root");
                            return defaultUnlocked;
                        })
                        .serialize(setCodec.fieldOf("nodes"))
                        .copyOnDeath()
                        .build();
            });


    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
