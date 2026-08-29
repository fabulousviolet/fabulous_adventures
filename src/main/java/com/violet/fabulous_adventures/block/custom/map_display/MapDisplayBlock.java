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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;


public class MapDisplayBlock extends Block implements EntityBlock {

    public static final EnumProperty<Direction> FACING;

    static{
        FACING = BlockStateProperties.FACING;
    }
    public MapDisplayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MapDisplay(pos, state);
    }


    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(FabulousItems.ADVANCED_MAP.get()) && level.getBlockEntity(pos) instanceof MapDisplay display) {
            MapId newId = itemStack.get(DataComponents.MAP_ID);
            if (newId == null) return InteractionResult.PASS;

            BlockPos anchorPos = display.isAnchor() ? pos : display.getAnchorPos();
            if (level.getBlockEntity(anchorPos) instanceof MapDisplay anchor) {
                anchor.makeAnchor(newId, anchor.getLocalU(), anchor.getLocalV(), anchor.getSquareSize());

                // propagate the new cachedMapId to every other member of the group too
                for (BlockPos memberPos : getSquareMembers(new MapDisplaySquare(anchorPos.getX(), anchorPos.getY(), anchorPos.getZ(), anchor.getSquareSize(), state.getValue(FACING).getAxis()))) {
                    if (!memberPos.equals(anchorPos) && level.getBlockEntity(memberPos) instanceof MapDisplay child) {
                        child.makeChild(anchorPos, newId, child.getLocalU(), child.getLocalV(), child.getSquareSize());
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static final List<Direction.Axis> scanX = List.of(Direction.Axis.Y, Direction.Axis.Z);
    public static final List<Direction.Axis> scanY = List.of(Direction.Axis.X, Direction.Axis.Z);
    public static final List<Direction.Axis> scanZ = List.of(Direction.Axis.X, Direction.Axis.Y);

    public static void getInterconnectedDisplays(Level level, Set<BlockPos> checkedPositions, Set<BlockPos> queue, Direction facing, Set<BlockPos> result) {
        Set<BlockPos> newQueue = new HashSet<>();
        Direction.Axis axis = facing.getAxis();

        for (BlockPos blockPos : queue) {
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(FabulousBlocks.MAP_DISPLAY.get()) && blockState.getValue(MapDisplayBlock.FACING) == facing && !result.contains(blockPos)) {
                result.add(blockPos);
            }

            List<Direction.Axis> scanAxes = switch (axis) {
                case X -> scanX;
                case Y -> scanY;
                case Z -> scanZ;
            };

            for (Direction.Axis scanAxis : scanAxes) {
                for (int step : Set.of(-1, 1)) {
                    BlockPos neighborPos = blockPos.relative(scanAxis, step);
                    BlockState neighborState = level.getBlockState(neighborPos);
                    if (neighborState.is(FabulousBlocks.MAP_DISPLAY.get())
                            && neighborState.getValue(MapDisplayBlock.FACING) == facing
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

        Set<BlockPos> neighbors = getDisplayNeighbors(level, pos, state.getValue(FACING)    );
        if (!neighbors.isEmpty()) {
            regroupDisplays(level, neighbors, state.getValue(FACING));
        }
    }

    public static Set<BlockPos> getDisplayNeighbors(Level level, BlockPos pos, Direction facing) {
        Set<BlockPos> neighbors = new HashSet<>();
        Direction.Axis axis = facing.getAxis();
        List<Direction.Axis> scanAxes = switch (axis) {
            case X -> scanX;
            case Y -> scanY;
            case Z -> scanZ;
        };

        for (Direction.Axis scanAxis : scanAxes) {
            for (int step : Set.of(-1, 1)) {
                BlockPos neighborPos = pos.relative(scanAxis, step);
                if (level.getBlockState(neighborPos).is(FabulousBlocks.MAP_DISPLAY.get()) && level.getBlockState(neighborPos).getValue(FACING) == facing) {
                    neighbors.add(neighborPos);
                }
            }
        }
        return neighbors;
    }

    public static Set<BlockPos> getSquareMembers(MapDisplaySquare square) {
        Set<BlockPos> members = new HashSet<>();
        BlockPos corner = new BlockPos(square.x(), square.y(), square.z());

        List<Direction.Axis> scanAxes = switch (square.axis()) {
            case X -> scanX;
            case Y -> scanY;
            case Z -> scanZ;
        };

        for (int u = 0; u < square.size(); u++) {
            for (int v = 0; v < square.size(); v++) {
                members.add(toBlockPos(square.axis(), corner.get(square.axis()),
                        corner.get(scanAxes.getFirst()) - u,
                        corner.get(scanAxes.getLast()) - v));
            }
        }
        return members;
    }

    public static void regroupDisplays(Level level, Set<BlockPos> searchRoots, Direction facing) {
        Set<BlockPos> allConnected = new HashSet<>();
        Direction.Axis axis = facing.getAxis();
        getInterconnectedDisplays(level, new HashSet<>(), searchRoots, facing, allConnected);

        Map<BlockPos, MapId> capturedIds = new HashMap<>();
        for (BlockPos pos : allConnected) {
            if (level.getBlockEntity(pos) instanceof MapDisplay display) {
                MapId id = display.resolveMapId();
                if (id != null) capturedIds.put(pos, id);
            }
        }

        List<Direction.Axis> scanAxes = switch (axis) {
            case X -> scanX;
            case Y -> scanY;
            case Z -> scanZ;
        };

        Set<BlockPos> remaining = new HashSet<>(allConnected);
        while (!remaining.isEmpty()) {
            MapDisplaySquare square = computeSquareDP(remaining, axis);
            Set<BlockPos> members = getSquareMembers(square);
            BlockPos anchorPos = new BlockPos(square.x(), square.y(), square.z());

            MapId inheritedId = null;
            for (BlockPos memberPos : members) {
                if (capturedIds.containsKey(memberPos)) {
                    inheritedId = capturedIds.get(memberPos);
                    break;
                }
            }

            for (BlockPos memberPos : members) {
                if (level.getBlockEntity(memberPos) instanceof MapDisplay display) {
                    int localU = anchorPos.get(scanAxes.getFirst()) - memberPos.get(scanAxes.getFirst());
                    int localV = anchorPos.get(scanAxes.getLast()) - memberPos.get(scanAxes.getLast());

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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public static MapDisplaySquare computeSquareDP(Set<BlockPos> input, Direction.Axis axis) {
        List<Direction.Axis> scanAxes = switch (axis) {
            case X -> scanX;
            case Y -> scanY;
            case Z -> scanZ;
        };

        int minAbsU = input.iterator().next().get(scanAxes.getFirst());
        int maxAbsU = minAbsU;
        int minAbsV = input.iterator().next().get(scanAxes.getLast());
        int maxAbsV = minAbsV;
        int thirdAxisValue = input.iterator().next().get(axis);

        for (BlockPos blockPos : input) {
            minAbsU = Math.min(minAbsU, blockPos.get(scanAxes.getFirst()));
            maxAbsU = Math.max(maxAbsU, blockPos.get(scanAxes.getFirst()));
            minAbsV = Math.min(minAbsV, blockPos.get(scanAxes.getLast()));
            maxAbsV = Math.max(maxAbsV, blockPos.get(scanAxes.getLast()));
        }

        int gridSizeU = maxAbsU - minAbsU + 1;
        int gridSizeV = maxAbsV - minAbsV + 1;

        boolean[][] map = new boolean[gridSizeU][gridSizeV];
        for (int u = 0; u < gridSizeU; u++) {
            for (int v = 0; v < gridSizeV; v++) {
                map[u][v] = input.contains(toBlockPos(axis, thirdAxisValue, u + minAbsU, v + minAbsV));
            }
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
                    bestU = u;
                    bestV = v;
                }
            }
        }

        BlockPos corner = toBlockPos(axis, thirdAxisValue, bestU + minAbsU, bestV + minAbsV);
        return new MapDisplaySquare(corner.getX(), corner.getY(), corner.getZ(), bestSize, axis);
    }

    private static BlockPos toBlockPos(Direction.Axis axis, int thirdAxisValue, int u, int v) {
        return switch (axis) {
            case X -> new BlockPos(thirdAxisValue, u, v);
            case Y -> new BlockPos(u, thirdAxisValue, v);
            case Z -> new BlockPos(u, v, thirdAxisValue);
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }


    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    private static final Map<Direction, VoxelShape> SHAPES;

    static{
        SHAPES = Shapes.rotateAll(Block.box(0.0F, 0.0F, 14.0F,16.0F,16.0F,16.0F));
    }
}