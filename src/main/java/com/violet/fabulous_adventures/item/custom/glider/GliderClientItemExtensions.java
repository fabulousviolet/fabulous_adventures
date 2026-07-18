package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousArmPoses;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;

public class GliderClientItemExtensions implements IClientItemExtensions {
    //returns the correct ArmPose when gliding
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, @NonNull InteractionHand hand, @NonNull ItemStack itemStack) {
        MobEffectInstance slowFalling = entityLiving.getEffect(MobEffects.SLOW_FALLING);
        if (slowFalling != null && slowFalling.getAmplifier() == 1) {
            return FabulousArmPoses.GLIDER_POSE;
        }
        return null;
    }
}
