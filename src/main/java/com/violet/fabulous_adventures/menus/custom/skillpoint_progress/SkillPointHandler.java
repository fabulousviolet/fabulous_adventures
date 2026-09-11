package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.violet.fabulous_adventures.menus.custom.skillpoint_progress.SkillProgressCalculator.calculateSkillPoints;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class SkillPointHandler {

    private static final int RECALC_INTERVAL_TICKS = 100;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % RECALC_INTERVAL_TICKS != 0) return;
        int pointsBeforeTick = player.getData(FabulousAttachments.SKILL_POINTS.get());
        int pointsAfterTick = calculateSkillPoints(player);
        if (pointsBeforeTick < pointsAfterTick) {
            player.setData(FabulousAttachments.SKILL_POINTS.get(), pointsAfterTick);
            event.getEntity().sendOverlayMessage(Component.literal("New skill point(s) unlocked. Press 'K' to open the skill tree."));
        } else if (pointsBeforeTick != pointsAfterTick) {
            player.setData(FabulousAttachments.SKILL_POINTS.get(), pointsAfterTick);
        }
    }

}


