package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public record SkillpointProgressEntry(
        SkillCriterionType type,
        String id,
        Identifier imageLoc,
        Component label,
        int index
) {}
