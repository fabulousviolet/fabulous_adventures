package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import net.minecraft.util.Mth;

public record MilestoneProgress(double current, double nextMilestone) {
    public float fillFraction() {
        if (nextMilestone <= 0) return 1f;
        return (float) Mth.clamp(current / nextMilestone, 0.0, 1.0);
    }
}