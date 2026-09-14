package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class GliderItem extends Item implements IClientItemExtensions {

    public GliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!SkillUtils.isUnlocked(player, "glider_unlock")) {
            if (!level.isClientSide()) {
                player.displayClientMessage(Component.literal("Glider is not unlocked yet. Unlock it in the Skill tree (K)"),true);
            }
            return InteractionResult.CONSUME;
        }

        ItemStack stack = player.getItemInHand(hand);
        boolean currentlyActive = stack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false);

        if (currentlyActive) {
            if (!level.isClientSide()) {
                stack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
            }
        } else if (level.getBlockState(player.getOnPos().below()).isAir()) {
            if (!level.isClientSide()) {
                stack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), true);
                stack.hurtAndBreak(1, player, getEquipmentSlot(stack));
            }
            GliderTickHandler.markJustActivated(player);
        }

        return InteractionResult.CONSUME;
    }
}


