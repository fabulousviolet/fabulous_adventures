package com.violet.fabulous_adventures.item.custom.claymore;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import org.jspecify.annotations.NonNull;

public class ClaymoreArmPoseTransformerRelease implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, @NonNull HumanoidRenderState entity, @NonNull HumanoidArm arm) {
        model.rightArm.xRot = (float) (-0.25*Math.PI);
        model.leftArm.xRot = (float) (-0.25*Math.PI);
        model.leftArm.yRot = (float) (0.1*Math.PI);
        model.rightArm.yRot = (float) (-0.1*Math.PI);

    }
}