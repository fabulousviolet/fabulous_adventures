package com.violet.fabulous_adventures;


import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreArmPoseTransformerCharging;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreArmPoseTransformerNormal;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreArmPoseTransformerRelease;
import com.violet.fabulous_adventures.item.custom.glider.GliderArmPoseTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class FabulousArmPoseParams {
    //init pose proxies here vvv
    public static final EnumProxy<HumanoidModel.ArmPose> GLIDER_POSE_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false, // twoHanded
            false, // affectsOffhandPose
            new GliderArmPoseTransformer()
    );
    public static final EnumProxy<HumanoidModel.ArmPose> CLAYMORE_POSE_NORMAL_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            true, // twoHanded
            true, // affectsOffhandPose
            new ClaymoreArmPoseTransformerNormal()
    );
    public static final EnumProxy<HumanoidModel.ArmPose> CLAYMORE_POSE_CHARGING_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            true, // twoHanded
            true, // affectsOffhandPose
            new ClaymoreArmPoseTransformerCharging()
    );
    public static final EnumProxy<HumanoidModel.ArmPose> CLAYMORE_POSE_RELEASE_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            true, // twoHanded
            true, // affectsOffhandPose
            new ClaymoreArmPoseTransformerRelease()
    );
}
