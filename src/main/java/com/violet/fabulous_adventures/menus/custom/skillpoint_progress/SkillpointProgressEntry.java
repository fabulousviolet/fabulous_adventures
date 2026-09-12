package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record SkillpointProgressEntry(
        SkillCriterionType type,
        String id,
        ResourceLocation imageLoc,
        Component label,
        int index
) {}
