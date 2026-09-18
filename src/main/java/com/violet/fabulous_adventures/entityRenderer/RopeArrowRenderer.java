package com.violet.fabulous_adventures.entityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.entity.custom.RopeArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RopeArrowRenderer extends ArrowRenderer<RopeArrow> {
    public RopeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    //define arrow texture location
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/entity/rope_arrow.png");
    //returns the texture location
    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull RopeArrow ropeArrow) {
        return TEXTURE;
    }


    //creates a new renderState


    @Override
    public void render(RopeArrow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
