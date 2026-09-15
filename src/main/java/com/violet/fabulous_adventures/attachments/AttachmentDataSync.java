package com.violet.fabulous_adventures.attachments;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Set;

public class AttachmentDataSync {
    public static void setSkillPoints(ServerPlayer player, int value) {
        player.setData(FabulousAttachments.SKILL_POINTS.get(), value);
        PacketDistributor.sendToPlayer(player, new SyncSkillPointsPayload(value));
    }

    public static void setBaseSkillPoints(ServerPlayer player, int value) {
        player.setData(FabulousAttachments.BASE_SKILL_POINTS.get(), value);
        PacketDistributor.sendToPlayer(player, new SyncBaseSkillPointsPayload(value));
    }

    public static void setUnlockedSkills(ServerPlayer player, Set<String> value) {
        player.setData(FabulousAttachments.UNLOCKED_SKILLS.get(), value);
        PacketDistributor.sendToPlayer(player, new SyncUnlockedSkillsPayload(value));
    }
}
