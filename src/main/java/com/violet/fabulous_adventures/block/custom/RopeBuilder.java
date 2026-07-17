package com.violet.fabulous_adventures.block.custom;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RopeBuilder extends Block {
    public static final int MAXROPELENGTH = 32;
    public RopeBuilder(Properties properties) {
        super(properties);
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.getBlockState(pos.above()).isAir();
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!canSurvive(state, level, pos)) {
            level.removeBlock(pos,false);
            return;
        }
        BlockState ropeState = FabulousBlocks.ROPE_CLIMBABLE.get().defaultBlockState()
                .setValue(RopeBlock.END,false)
                .setValue(RopeBlock.AXIS, Direction.Axis.Y);


        for (int i = 0; i <= MAXROPELENGTH; i++) {
            BlockPos currentPos = pos.relative(Direction.Axis.Y,-i);
            boolean hitGround = !level.getBlockState(currentPos.below()).isAir() && !level.getBlockState(currentPos.below()).canBeReplaced();
            boolean atLimit = i == MAXROPELENGTH;

            if (hitGround || atLimit) {
                level.setBlock(currentPos, ropeState.setValue(RopeBlock.END, true), Block.UPDATE_ALL);
                break;
            }
            else{
                level.setBlock(currentPos,ropeState,Block.UPDATE_ALL);

            }
        }
    }
}
