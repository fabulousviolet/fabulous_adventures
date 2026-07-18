package com.violet.fabulous_adventures.item.custom.glider;


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import org.jspecify.annotations.NonNull;
//a class to adjust the ArmPose
public class GliderArmPoseTransformer implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, @NonNull HumanoidRenderState entity, @NonNull HumanoidArm arm) {
        model.rightArm.xRot = (float) -Math.PI;
        model.leftArm.xRot = (float) -Math.PI;
        model.rightLeg.xRot = 0;
        model.leftLeg.xRot = 0;

    }
}
