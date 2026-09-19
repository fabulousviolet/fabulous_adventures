package com.violet.fabulous_adventures.skills;

import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import net.minecraft.world.entity.player.Player;

public class SkillUtils {
    public static boolean isUnlocked(Player player, String skillId) {
        return player.getData(FabulousAttachments.UNLOCKED_SKILLS.get()).contains(skillId);
    }
}