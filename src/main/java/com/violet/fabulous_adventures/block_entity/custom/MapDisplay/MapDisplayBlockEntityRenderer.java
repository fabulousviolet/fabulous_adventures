package com.violet.fabulous_adventures.block_entity.custom.MapDisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.violet.fabulous_adventures.block.custom.map_display.MapDisplayBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.MapTextureManager;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class MapDisplayBlockEntityRenderer implements BlockEntityRenderer<MapDisplay> {
    private final MapTextureManager mapTextureManager;

    public MapDisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.mapTextureManager = Minecraft.getInstance().getMapTextureManager();
    }

    @Override
    public void render(MapDisplay blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        MapId mapId = blockEntity.resolveMapId();
        if (mapId == null) return;

        Level level = blockEntity.getLevel();
        MapItemSavedData mapData = level.getMapData(mapId);
        if (mapData == null) return;

        var texture = mapTextureManager.prepareMapTexture(mapId, mapData);

        int localU = blockEntity.getLocalU();
        int localV = blockEntity.getLocalV();
        int squareSize = blockEntity.getSquareSize();
        Direction facing = blockEntity.getBlockState().getValue(MapDisplayBlock.FACING);

        float u0 = (float) localU / squareSize;
        float u1 = (float) (localU + 1) / squareSize;
        float v0 = (float) localV / squareSize;
        float v1 = (float) (localV + 1) / squareSize;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        switch (facing) {
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case NORTH -> {}
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }

        poseStack.translate(-0.5, -0.5, -0.5);

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.text(texture));
        Matrix4f pose = poseStack.last().pose();

        buffer.addVertex(pose, 0.0F, 1.0F, 0.0F).setColor(-1).setUv(u0, v1).setLight(packedLight);
        buffer.addVertex(pose, 1.0F, 1.0F, 0.0F).setColor(-1).setUv(u1, v1).setLight(packedLight);
        buffer.addVertex(pose, 1.0F, 0.0F, 0.0F).setColor(-1).setUv(u1, v0).setLight(packedLight);
        buffer.addVertex(pose, 0.0F, 0.0F, 0.0F).setColor(-1).setUv(u0, v0).setLight(packedLight);

        poseStack.popPose();
    }
}