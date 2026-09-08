package com.violet.fabulous_adventures.menus;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FabulousMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, FabulousAdventures.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SkilltreeMenu>> SKILLTREE = MENUS.register("skilltree", () -> new MenuType<>(SkilltreeMenu::new, FeatureFlags.DEFAULT_FLAGS));


    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

