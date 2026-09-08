package com.violet.fabulous_adventures.block_entity.custom.OxygenTank;

import com.mojang.blaze3d.vertex.PoseStack;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class OxygenTankBlockEntityRenderer implements BlockEntityRenderer<OxygenTank,OxygenTankRenderState> {

    public OxygenTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public OxygenTankRenderState createRenderState() {
        return new OxygenTankRenderState();
    }

    @Override
    public void extractRenderState(OxygenTank blockEntity, OxygenTankRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);
        renderState.fillFraction = (float) blockEntity.getFillValue() / OxygenTank.getMaxValue();
    }

    @Override
    public void submit(OxygenTankRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.fillFraction <= 0f) return;

        float inset = 0.126f;   // keeps the column inset from the block edges, inside the "window" frame
        float x0 = inset, x1 = 1f - inset;
        float z0 = inset, z1 = 1f - inset;
        float y0 = inset;
        float y1 = inset + (1f - 2 * inset) * renderState.fillFraction;

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"textures/block/oxygen_tank_air.png")), (pose, buffer) -> {
            int r = 255, g = 255, b = 255, a = 130;
            int light = 15728880;

            // +X face
            buffer.addVertex(pose, x1, y0, z0).setColor(r,g,b,a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
            buffer.addVertex(pose, x1, y0, z1).setColor(r,g,b,a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
            buffer.addVertex(pose, x1, y1, z1).setColor(r,g,b,a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);
            buffer.addVertex(pose, x1, y1, z0).setColor(r,g,b,a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(1, 0, 0);

            // -X face
            buffer.addVertex(pose, x0, y0, z1).setColor(r,g,b,a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
            buffer.addVertex(pose, x0, y0, z0).setColor(r,g,b,a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
            buffer.addVertex(pose, x0, y1, z0).setColor(r,g,b,a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);
            buffer.addVertex(pose, x0, y1, z1).setColor(r,g,b,a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(-1, 0, 0);

            // +Z face
            buffer.addVertex(pose, x1, y0, z1).setColor(r,g,b,a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
            buffer.addVertex(pose, x0, y0, z1).setColor(r,g,b,a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
            buffer.addVertex(pose, x0, y1, z1).setColor(r,g,b,a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);
            buffer.addVertex(pose, x1, y1, z1).setColor(r,g,b,a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, 1);

            // -Z face
            buffer.addVertex(pose, x0, y0, z0).setColor(r,g,b,a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
            buffer.addVertex(pose, x1, y0, z0).setColor(r,g,b,a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
            buffer.addVertex(pose, x1, y1, z0).setColor(r,g,b,a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);
            buffer.addVertex(pose, x0, y1, z0).setColor(r,g,b,a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 0, -1);

            // top face
            buffer.addVertex(pose, x0, y1, z0).setColor(r,g,b,a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(pose, x1, y1, z0).setColor(r,g,b,a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(pose, x1, y1, z1).setColor(r,g,b,a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(pose, x0, y1, z1).setColor(r,g,b,a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0, 1, 0);
        });

    }

}
