package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.menus.FabulousMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SkillpointProgressMenu extends AbstractContainerMenu {
    public SkillpointProgressMenu(int containerId, Inventory playerInventory) {
        super(FabulousMenus.SKILLPOINT_PROGRESS.get(),containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
