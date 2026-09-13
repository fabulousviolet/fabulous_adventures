package com.violet.fabulous_adventures.block.custom.map_display;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block_entity.custom.MapDisplay.MapDisplay;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.*;

public class MapDisplayBlock extends Block implements EntityBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public MapDisplayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MapDisplay(pos, state);
    }

    // === Tiling Mapping Helpers ===
    public static Direction getRightDir(Direction facing) {
        return switch (facing) {
            case NORTH -> Direction.WEST;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.SOUTH;
            case EAST -> Direction.NORTH;
            case UP, DOWN -> Direction.EAST;
        };
    }

    public static Direction getDownDir(Direction facing) {
        return switch (facing) {
            case NORTH, SOUTH, WEST, EAST -> Direction.DOWN;
            case UP -> Direction.SOUTH;
            case DOWN -> Direction.NORTH;
        };
    }

    public static int getCoord(BlockPos pos, Direction dir) {
        return pos.get(dir.getAxis()) * dir.getAxisDirection().getStep();
    }

    public static BlockPos fromUV(int u, int v, Direction facing, int planeValue) {
        int x = 0, y = 0, z = 0;

        switch (facing.getAxis()) {
            case X -> x = planeValue;
            case Y -> y = planeValue;
            case Z -> z = planeValue;
        }

        Direction right = getRightDir(facing);
        Direction down = getDownDir(facing);

        if (right.getAxis() == Direction.Axis.X) x = u * right.getAxisDirection().getStep();
        else if (right.getAxis() == Direction.Axis.Y) y = u * right.getAxisDirection().getStep();
        else if (right.getAxis() == Direction.Axis.Z) z = u * right.getAxisDirection().getStep();

        if (down.getAxis() == Direction.Axis.X) x = v * down.getAxisDirection().getStep();
        else if (down.getAxis() == Direction.Axis.Y) y = v * down.getAxisDirection().getStep();
        else if (down.getAxis() == Direction.Axis.Z) z = v * down.getAxisDirection().getStep();

        return new BlockPos(x, y, z);
    }
    // ==============================

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(FabulousItems.ADVANCED_MAP.get()) && level.getBlockEntity(pos) instanceof MapDisplay display) {
            MapId newId = itemStack.get(DataComponents.MAP_ID);
            if (newId == null) return InteractionResult.PASS;

            BlockPos anchorPos = display.isAnchor() ? pos : display.getAnchorPos();
            if (level.getBlockEntity(anchorPos) instanceof MapDisplay anchor) {
                anchor.makeAnchor(newId, anchor.getLocalU(), anchor.getLocalV(), anchor.getSquareSize());

                for (BlockPos memberPos : getSquareMembers(new MapDisplaySquare(anchorPos.getX(), anchorPos.getY(), anchorPos.getZ(), anchor.getSquareSize(), state.getValue(FACING)))) {
                    if (!memberPos.equals(anchorPos) && level.getBlockEntity(memberPos) instanceof MapDisplay child) {
                        child.makeChild(anchorPos, newId, child.getLocalU(), child.getLocalV(), child.getSquareSize());
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static void getInterconnectedDisplays(Level level, Set<BlockPos> checkedPositions, Set<BlockPos> queue, Direction facing, Set<BlockPos> result) {
        Set<BlockPos> newQueue = new HashSet<>();

        for (BlockPos blockPos : queue) {
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(FabulousBlocks.MAP_DISPLAY.get()) && blockState.getValue(FACING) == facing && !result.contains(blockPos)) {
                result.add(blockPos);
            }

            // Look at all neighbors in the same plane
            for (Direction dir : Direction.values()) {
                if (dir.getAxis() != facing.getAxis()) {
                    BlockPos neighborPos = blockPos.relative(dir);
                    BlockState neighborState = level.getBlockState(neighborPos);
                    if (neighborState.is(FabulousBlocks.MAP_DISPLAY.get())
                            && neighborState.getValue(FACING) == facing
                            && !checkedPositions.contains(neighborPos)
                            && !result.contains(neighborPos)) {
                        newQueue.add(neighborPos);
                        result.add(neighborPos);
                    }
                }
            }
            checkedPositions.add(blockPos);
        }

        if (newQueue.isEmpty()) return;
        getInterconnectedDisplays(level, checkedPositions, newQueue, facing, result);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level.isClientSide()) return;
        regroupDisplays(level, Set.of(pos), state.getValue(FACING));
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
        Set<BlockPos> neighbors = getDisplayNeighbors(level, pos, state.getValue(FACING));
        if (!neighbors.isEmpty()) {
            regroupDisplays(level, neighbors, state.getValue(FACING));
        }
    }

    public static Set<BlockPos> getDisplayNeighbors(Level level, BlockPos pos, Direction facing) {
        Set<BlockPos> neighbors = new HashSet<>();
        for (Direction dir : Direction.values()) {
            if (dir.getAxis() != facing.getAxis()) {
                BlockPos neighborPos = pos.relative(dir);
                if (level.getBlockState(neighborPos).is(FabulousBlocks.MAP_DISPLAY.get()) && level.getBlockState(neighborPos).getValue(FACING) == facing) {
                    neighbors.add(neighborPos);
                }
            }
        }
        return neighbors;
    }

    public static Set<BlockPos> getSquareMembers(MapDisplaySquare square) {
        Set<BlockPos> members = new HashSet<>();
        BlockPos anchorPos = new BlockPos(square.x(), square.y(), square.z());
        Direction facing = square.facing();

        int planeValue = anchorPos.get(facing.getAxis());
        int anchorU = getCoord(anchorPos, getRightDir(facing));
        int anchorV = getCoord(anchorPos, getDownDir(facing));

        for (int u = 0; u < square.size(); u++) {
            for (int v = 0; v < square.size(); v++) {
                members.add(fromUV(anchorU + u, anchorV + v, facing, planeValue));
            }
        }
        return members;
    }

    public static void regroupDisplays(Level level, Set<BlockPos> searchRoots, Direction facing) {
        Set<BlockPos> allConnected = new HashSet<>();
        getInterconnectedDisplays(level, new HashSet<>(), searchRoots, facing, allConnected);

        Map<BlockPos, MapId> capturedIds = new HashMap<>();
        for (BlockPos pos : allConnected) {
            if (level.getBlockEntity(pos) instanceof MapDisplay display) {
                MapId id = display.resolveMapId();
                if (id != null) capturedIds.put(pos, id);
            }
        }

        Set<BlockPos> remaining = new HashSet<>(allConnected);
        while (!remaining.isEmpty()) {
            MapDisplaySquare square = computeSquareDP(remaining, facing);
            if (square == null) break;

            Set<BlockPos> members = getSquareMembers(square);
            BlockPos anchorPos = new BlockPos(square.x(), square.y(), square.z());

            MapId inheritedId = null;
            for (BlockPos memberPos : members) {
                if (capturedIds.containsKey(memberPos)) {
                    inheritedId = capturedIds.get(memberPos);
                    break;
                }
            }

            int anchorU = getCoord(anchorPos, getRightDir(facing));
            int anchorV = getCoord(anchorPos, getDownDir(facing));

            for (BlockPos memberPos : members) {
                if (level.getBlockEntity(memberPos) instanceof MapDisplay display) {
                    int memberU = getCoord(memberPos, getRightDir(facing));
                    int memberV = getCoord(memberPos, getDownDir(facing));

                    int localU = memberU - anchorU;
                    int localV = memberV - anchorV;

                    if (memberPos.equals(anchorPos)) {
                        display.makeAnchor(inheritedId, localU, localV, square.size());
                    } else {
                        display.makeChild(anchorPos, inheritedId, localU, localV, square.size());
                    }
                }
            }
            remaining.removeAll(members);
        }
    }

    public static MapDisplaySquare computeSquareDP(Set<BlockPos> input, Direction facing) {
        if (input.isEmpty()) return null;

        BlockPos first = input.iterator().next();
        int planeValue = first.get(facing.getAxis());

        int minU = Integer.MAX_VALUE;
        int maxU = Integer.MIN_VALUE;
        int minV = Integer.MAX_VALUE;
        int maxV = Integer.MIN_VALUE;

        // Map inputs to a unified 2D coordinate space regardless of block face
        for (BlockPos pos : input) {
            int u = getCoord(pos, getRightDir(facing));
            int v = getCoord(pos, getDownDir(facing));
            minU = Math.min(minU, u);
            maxU = Math.max(maxU, u);
            minV = Math.min(minV, v);
            maxV = Math.max(maxV, v);
        }

        int gridSizeU = maxU - minU + 1;
        int gridSizeV = maxV - minV + 1;

        boolean[][] map = new boolean[gridSizeU][gridSizeV];
        for (BlockPos pos : input) {
            int u = getCoord(pos, getRightDir(facing));
            int v = getCoord(pos, getDownDir(facing));
            map[u - minU][v - minV] = true;
        }

        int[][] dp = new int[gridSizeU][gridSizeV];
        int bestSize = 0, bestU = 0, bestV = 0;

        for (int u = 0; u < gridSizeU; u++) {
            for (int v = 0; v < gridSizeV; v++) {
                if (!map[u][v]) continue;

                dp[u][v] = (u == 0 || v == 0)
                        ? 1
                        : 1 + Math.min(Math.min(dp[u - 1][v], dp[u][v - 1]), dp[u - 1][v - 1]);

                if (dp[u][v] > bestSize) {
                    bestSize = dp[u][v];
                    bestU = u; // In standard DP this tracks the Bottom-Right corner
                    bestV = v;
                }
            }
        }

        // Trace back to find the Top-Left anchor corner in local UV
        int anchorLocalU = bestU - bestSize + 1;
        int anchorLocalV = bestV - bestSize + 1;

        // Convert to absolute UV then back to BlockPos
        BlockPos anchorPos = fromUV(anchorLocalU + minU, anchorLocalV + minV, facing, planeValue);

        return new MapDisplaySquare(anchorPos.getX(), anchorPos.getY(), anchorPos.getZ(), bestSize, facing);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    private static final Map<Direction, VoxelShape> SHAPES;

    static {
        SHAPES = Shapes.rotateAll(Block.box(0.0F, 0.0F, 14.0F, 16.0F, 16.0F, 16.0F));
    }
}