package com.violet.fabulous_adventures.item.custom.claymore;

import com.violet.fabulous_adventures.FabulousArmPoses;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;

public class ClaymoreItemClientExtensions implements IClientItemExtensions {

    @Override
    public HumanoidModel.ArmPose getArmPose(@NonNull LivingEntity entityLiving, @NonNull InteractionHand hand, @NonNull ItemStack itemStack) {
        ClaymoreChargeState state = itemStack.getOrDefault(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
        if (state == ClaymoreChargeState.NORMAL) {
            return FabulousArmPoses.CLAYMORE_POSE_NORMAL;
        }
        if (state == ClaymoreChargeState.CHARGED) {
            return FabulousArmPoses.CLAYMORE_POSE_CHARGING;
        }
        if (state == ClaymoreChargeState.RELEASE) {
            return FabulousArmPoses.CLAYMORE_POSE_RELEASE;
        }

        return null;
    }
}
