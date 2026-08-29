package com.violet.fabulous_adventures.block_entity.custom.MapDisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.violet.fabulous_adventures.block.custom.map_display.MapDisplayBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.MapTextureManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MapDisplayBlockEntityRenderer implements BlockEntityRenderer<MapDisplay, MapDisplayRenderState> {
    private final MapTextureManager mapTextureManager;

    public MapDisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.mapTextureManager = Minecraft.getInstance().getMapTextureManager();
    }

    @Override
    public MapDisplayRenderState createRenderState() {
        return new MapDisplayRenderState();
    }

    @Override
    public void extractRenderState(MapDisplay blockEntity, MapDisplayRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);

        renderState.mapId = blockEntity.resolveMapId();
        renderState.localU = blockEntity.getLocalU();
        renderState.localV = blockEntity.getLocalV();
        renderState.squareSize = blockEntity.getSquareSize();
        renderState.facing = blockEntity.getBlockState().getValue(MapDisplayBlock.FACING);
    }

    @Override
    public void submit(MapDisplayRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.mapId == null) return;

        Level level = Minecraft.getInstance().level;
        MapItemSavedData mapData = level.getMapData(renderState.mapId);
        if (mapData == null) return;

        var texture = mapTextureManager.prepareMapTexture(renderState.mapId, mapData);

        int tileX, tileY;
        boolean mirrorX = false,
                mirrorY = false;

        switch (renderState.facing) {
            case NORTH -> { tileX = renderState.localU; tileY = renderState.localV; }
            case SOUTH -> { tileX = renderState.localU; tileY = renderState.localV; mirrorX = true; }
            case EAST  -> { tileX = renderState.localV; tileY = renderState.localU; }
            case WEST  -> { tileX = renderState.localV; tileY = renderState.localU; mirrorX = true; }
            case UP    -> { tileX = renderState.localU; tileY = renderState.localV; mirrorX = true; mirrorY = true; }
            case DOWN  -> { tileX = renderState.localU; tileY = renderState.localV; mirrorX = true;}
            default -> { tileX = renderState.localU; tileY = renderState.localV; }
        }

        if (mirrorX) tileX = renderState.squareSize - 1 - tileX;
        if (mirrorY) tileY = renderState.squareSize - 1 - tileY;

        float u0 = (float) tileX / renderState.squareSize;
        float u1 = (float) (tileX + 1) / renderState.squareSize;
        float v0 = (float) tileY / renderState.squareSize;
        float v1 = (float) (tileY + 1) / renderState.squareSize;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        switch (renderState.facing) {
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case SOUTH -> {}
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }

        poseStack.translate(-0.5, -0.5, -0.374);

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.text(texture), (pose, buffer) -> {
            buffer.addVertex(pose, 0.0F, 0.0F, 0.0F).setColor(-1).setUv(u0, v1).setLight(15728880); // was v0
            buffer.addVertex(pose, 1.0F, 0.0F, 0.0F).setColor(-1).setUv(u1, v1).setLight(15728880); // was v0
            buffer.addVertex(pose, 1.0F, 1.0F, 0.0F).setColor(-1).setUv(u1, v0).setLight(15728880); // was v1
            buffer.addVertex(pose, 0.0F, 1.0F, 0.0F).setColor(-1).setUv(u0, v0).setLight(15728880); // was v1
        });

        poseStack.popPose();
    }
}
