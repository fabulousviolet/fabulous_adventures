package com.violet.fabulous_adventures;

import com.violet.fabulous_adventures.item.custom.glider.GliderArmPoseTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class FabulousArmPoseParams {
    public static final EnumProxy<HumanoidModel.ArmPose> GLIDER_POSE_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false, // twoHanded
            false, // affectsOffhandPose
            new GliderArmPoseTransformer()
    );
}
