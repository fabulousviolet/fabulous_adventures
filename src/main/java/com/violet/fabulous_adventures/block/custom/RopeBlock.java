package com.violet.fabulous_adventures.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;


public class RopeBlock extends RotatedPillarBlock {
    private static final Map<Direction.Axis, VoxelShape> SHAPES;
    public RopeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape)SHAPES.get(state.getValue(AXIS));
    }
    static {
        SHAPES = Shapes.rotateAllAxis(Block.cube((double)4.0F, (double)4.0F, (double)16.0F));
    }
}
