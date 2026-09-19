package com.violet.fabulous_adventures.skills;

import net.minecraft.world.entity.player.Player;

public abstract class Skill {
    public abstract void tick(Player player, boolean unlocked);
}