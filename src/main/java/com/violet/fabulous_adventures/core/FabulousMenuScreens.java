package com.violet.fabulous_adventures.core;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.menus.FabulousMenus;
import com.violet.fabulous_adventures.menus.custom.skillpoint_progress.SkillpointProgressScreen;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID, value = Dist.CLIENT)
public class FabulousMenuScreens {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(FabulousMenus.SKILLTREE.get(), SkilltreeScreen::new);
        event.register(FabulousMenus.SKILLPOINT_PROGRESS.get(), SkillpointProgressScreen::new);
    }
}
