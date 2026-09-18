package com.violet.fabulous_adventures.attachments;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, bus = EventBusSubscriber.Bus.GAME)

public class AttachmentLoginHandler {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        AttachmentDataSync.setSkillPoints(player, player.getData(FabulousAttachments.SKILL_POINTS.get()));
        AttachmentDataSync.setBaseSkillPoints(player, player.getData(FabulousAttachments.BASE_SKILL_POINTS.get()));
        AttachmentDataSync.setUnlockedSkills(player, player.getData(FabulousAttachments.UNLOCKED_SKILLS.get()));
    }
}
