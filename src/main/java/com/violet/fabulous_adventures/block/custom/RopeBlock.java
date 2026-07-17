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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;


public class RopeBlock extends RotatedPillarBlock {
    private static final Map<Direction.Axis, VoxelShape> SHAPES;

    public RopeBlock(Properties properties, boolean climbable) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(END, false));
        this.registerDefaultState(this.defaultBlockState().setValue(CLIMBABLE, climbable));
    }

    public static final BooleanProperty END = BooleanProperty.create("end");
    public static final BooleanProperty CLIMBABLE = BooleanProperty.create("climbable");

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape) SHAPES.get(state.getValue(AXIS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS, END, CLIMBABLE});
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {

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
        static{
            SHAPES = Shapes.rotateAllAxis(Block.cube((double) 4.0F, (double) 4.0F, (double) 16.0F));
        }

}

