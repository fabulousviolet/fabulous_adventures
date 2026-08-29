package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.menus.FabulousMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SkilltreeMenu extends AbstractContainerMenu {


    public SkilltreeMenu(int containerId, Inventory playerInventory) {
        super(FabulousMenus.SKILLTREE.get(),containerId);
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
