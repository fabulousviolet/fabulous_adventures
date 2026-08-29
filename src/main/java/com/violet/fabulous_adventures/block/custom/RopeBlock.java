package com.violet.fabulous_adventures.block.custom;


import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;


public class RopeBlock extends RotatedPillarBlock {
    private static final Map<Direction.Axis, VoxelShape> SHAPES;
//constructor for Default states
    public RopeBlock(Properties properties, boolean climbable) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(END, false));
        this.registerDefaultState(this.defaultBlockState().setValue(CLIMBABLE, climbable));
    }
//define Properties
    public static final BooleanProperty END = BooleanProperty.create("end");
    public static final BooleanProperty CLIMBABLE = BooleanProperty.create("climbable");
//rotate the voxel shape depending on the Axis
    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(AXIS));
    }
//define Block states
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, END, CLIMBABLE);
    }
//break all climbable rope blocks below the broken block
    @Override
    public boolean onDestroyedByPlayer(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull ItemStack toolStack, boolean willHarvest, @NonNull FluidState fluid) {

        if (state.getValue(CLIMBABLE)) {
            BlockPos current = pos.below();

            while (level.getBlockState(current).is(FabulousBlocks.ROPE_CLIMBABLE.get())) {
                if (level.isClientSide()) {
                    level.setBlock(current, fluid.createLegacyBlock(), 11);
                } else {
                    level.removeBlock(current, false);
                }
                current = current.below();
            }
            if (level.isClientSide()) {
                level.setBlock(pos, fluid.createLegacyBlock(), 11);
            } else {
                level.removeBlock(pos, false);
            }

            BlockPos above = pos.above();
            BlockState aboveState = level.getBlockState(above);
            if (aboveState.is(FabulousBlocks.ROPE_CLIMBABLE.get())) {
                level.setBlock(above, aboveState.setValue(END, true), Block.UPDATE_ALL);
            }
        }
        else{
            super.onDestroyedByPlayer(state,level,pos,player,toolStack,willHarvest,fluid);
        }
        return true;
    }
    //define voxel size
        static{
            SHAPES = Shapes.rotateAllAxis(Block.cube(4.0F, 4.0F, 16.0F));
        }

}

