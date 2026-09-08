package com.violet.fabulous_adventures.skills.custom;

import com.violet.fabulous_adventures.core.FabulousKeybinds;
import com.violet.fabulous_adventures.skills.Skill;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

public class CrawlSkill extends Skill {
    @Override
    public void tick(Player player, boolean unlocked) {
        if (!unlocked) {
            return;
        }
        if(FabulousKeybinds.CRAWL_SKILL_KEYBIND.isDown()) {
            player.setPose(Pose.SWIMMING);
        }
    }
}
