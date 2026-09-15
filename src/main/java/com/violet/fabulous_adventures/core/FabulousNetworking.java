package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.attachments.SyncBaseSkillPointsPayload;
import com.violet.fabulous_adventures.attachments.SyncSkillPointsPayload;
import com.violet.fabulous_adventures.attachments.SyncUnlockedSkillsPayload;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.menus.custom.skillpoint_progress.OpenSkillpointProgressPayload;
import com.violet.fabulous_adventures.menus.custom.skillpoint_progress.SkillpointProgressMenu;
import com.violet.fabulous_adventures.menus.custom.skilltree.OpenSkilltreePayload;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeButtonPayload;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeLogic;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class FabulousNetworking {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                OpenSkilltreePayload.TYPE,
                OpenSkilltreePayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        player.openMenu(new SimpleMenuProvider(
                                (containerId, inventory, p) -> new SkilltreeMenu(containerId, inventory),
                                Component.translatable("menu.fabulousadventures.skilltree")
                        ));
                    });
                }
        );
        registrar.playToServer(
                OpenSkillpointProgressPayload.TYPE,
                OpenSkillpointProgressPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        player.getStats().markAllDirty();
                        player.getStats().sendStats(player);
                        player.openMenu(new SimpleMenuProvider(
                                (containerId, inventory, p) -> new SkillpointProgressMenu(containerId, inventory),
                                Component.translatable("menu.fabulousadventures.skillpoint_progress")
                        ));
                    });
                }
        );
        registrar.playToServer(
                SkilltreeButtonPayload.TYPE,
                SkilltreeButtonPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();
                        new SkilltreeLogic(player, payload.nodeId(), payload.cost(), payload.parent());

                    });
                }
        );

        registrar.playToClient(SyncSkillPointsPayload.TYPE, SyncSkillPointsPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) player.setData(FabulousAttachments.SKILL_POINTS.get(), payload.value());
                }));

        registrar.playToClient(SyncBaseSkillPointsPayload.TYPE, SyncBaseSkillPointsPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) player.setData(FabulousAttachments.BASE_SKILL_POINTS.get(), payload.value());
                }));

        registrar.playToClient(SyncUnlockedSkillsPayload.TYPE, SyncUnlockedSkillsPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) player.setData(FabulousAttachments.UNLOCKED_SKILLS.get(), payload.value());
                }));
    }
}
