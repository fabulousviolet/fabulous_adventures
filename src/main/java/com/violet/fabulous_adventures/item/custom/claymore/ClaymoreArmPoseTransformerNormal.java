package com.violet.fabulous_adventures.item.custom.claymore;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class ClaymoreArmPoseTransformerNormal implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, LivingEntity livingEntity, HumanoidArm humanoidArm) {
        model.rightArm.xRot = (float) (-0.5*Math.PI);
        model.leftArm.xRot = (float) (-0.5*Math.PI);
        model.leftArm.yRot = (float) (0.25*Math.PI);
    }
}
