package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;

public class GliderItem extends Item implements IClientItemExtensions {


    public GliderItem(Properties properties) {
        super(properties);
    }
    //toggles the data component "GliderActive()"
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (SkillUtils.isUnlocked(player, "glider_unlock")) {
            ItemStack stack = player.getItemInHand(hand);
            boolean currentlyActive = stack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false);

            if (currentlyActive) {
                stack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
            } else {
                if (level.getBlockState(player.getOnPos().below()).isAir()) {
                    stack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), true);
                    stack.hurtAndBreak(1, player, hand);
                    GliderTickHandler.setInitialSpeed(player, GliderTickHandler.MIN_GLIDE_SPEED);
                    GliderTickHandler.setInitialHeading(player);
                }
            }
        } else {
            player.sendOverlayMessage(Component.literal("Glider is not unlocked yet. Unlock it in the Skill tree (K)"));
        }
        return InteractionResult.CONSUME;
    }
}



