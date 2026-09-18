package com.violet.fabulous_adventures.item.custom.claymore;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class ClaymoreArmPoseTransformerCharging implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, LivingEntity livingEntity, HumanoidArm humanoidArm) {
        model.rightArm.xRot = (float) (-Math.PI);
        model.leftArm.xRot = (float) (-Math.PI);
        model.leftArm.zRot = (float) (-0.1*Math.PI);
        model.rightArm.zRot = (float) (0.1*Math.PI);
    }
}
