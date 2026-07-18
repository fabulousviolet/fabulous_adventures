package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousArmPoses;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.model.HumanoidModel;
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

import java.util.function.Consumer;

public class GliderItem extends Item implements IClientItemExtensions {
    public GliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }


        if (player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.removeEffect(MobEffects.SLOW_FALLING);
            player.getItemInHand(hand).set(FabulousDataComponents.GLIDER_ACTIVE.get(),false);
        } else {
            if (level.getBlockState(player.getOnPos().below()).isAir()) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, -1, 1, false, false));
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                player.getItemInHand(hand).set(FabulousDataComponents.GLIDER_ACTIVE.get(),true);
            }
        }
        return InteractionResult.CONSUME;
    }



    private boolean isGliding(LivingEntity entity) {
        MobEffectInstance slowFalling = entity.getEffect(MobEffects.SLOW_FALLING);
        return slowFalling != null && slowFalling.getAmplifier() == 1;
    }



}



