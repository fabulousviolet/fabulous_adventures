package com.violet.fabulous_adventures.block_entity.custom.MapDisplay;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.saveddata.maps.MapId;

public class MapDisplayRenderState extends BlockEntityRenderState {
    public MapId mapId;
    public int localU, localV, squareSize;
    public Direction facing;
}
