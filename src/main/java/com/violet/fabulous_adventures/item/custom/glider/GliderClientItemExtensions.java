package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.core.FabulousArmPoses;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class GliderClientItemExtensions implements IClientItemExtensions {
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false)) {
            return FabulousArmPoses.GLIDER_POSE;
        }
        return null;
    }
}
