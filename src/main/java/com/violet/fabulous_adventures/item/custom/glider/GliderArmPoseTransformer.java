package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class GliderArmPoseTransformer implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
        model.rightArm.xRot = (float) -Math.PI;
        model.leftArm.xRot = (float) -Math.PI;
        model.rightLeg.xRot = 0;
        model.leftLeg.xRot = 0;

    }
}
