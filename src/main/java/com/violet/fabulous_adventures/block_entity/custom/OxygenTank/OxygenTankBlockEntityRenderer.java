package com.violet.fabulous_adventures.block_entity.custom.OxygenTank;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IBlockGetterExtension;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class OxygenTankBlockEntityRenderer implements BlockEntityRenderer<OxygenTank> {

    public OxygenTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }


    @Override
    public void render(OxygenTank oxygenTank, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, Vec3 vec3) {
        float fillFraction = oxygenTank.getFillValue() / OxygenTank.getMaxValue();
        if (fillFraction <= 0f) return;

        float inset = 2.1f / 16f;
        float x0 = inset, x1 = 1f - inset;
        float z0 = inset, z1 = 1f - inset;
        float y0 = inset;
        float y1 = inset + (1f - 2 * inset) * fillFraction;

        VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/block/oxygen_tank_air.png")));
        Matrix4f pose = poseStack.last().pose();
        int r = 255, g = 255, b = 255, a = 130;
        int light = 15728880;

        // +X face
        buffer.addVertex(pose, x1, y0, z0).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(pose, x1, y0, z1).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(pose, x1, y1, z0).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);

        // -X face
        buffer.addVertex(pose, x0, y0, z1).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(pose, x0, y0, z0).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(pose, x0, y1, z0).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(pose, x0, y1, z1).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);

        // +Z face
        buffer.addVertex(pose, x1, y0, z1).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(pose, x0, y0, z1).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(pose, x0, y1, z1).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);

        // -Z face
        buffer.addVertex(pose, x0, y0, z0).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(pose, x1, y0, z0).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(pose, x1, y1, z0).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(pose, x0, y1, z0).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);

        // top face
        buffer.addVertex(pose, x0, y1, z0).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
        buffer.addVertex(pose, x1, y1, z0).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
        buffer.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
        buffer.addVertex(pose, x0, y1, z1).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
    }
}


