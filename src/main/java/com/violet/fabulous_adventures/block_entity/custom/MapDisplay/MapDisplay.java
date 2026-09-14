package com.violet.fabulous_adventures.block_entity.custom.MapDisplay;

import com.violet.fabulous_adventures.block_entity.FabulousBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapId;


import javax.annotation.Nullable;

public class MapDisplay extends BlockEntity {
    private @Nullable MapId mapId;        // only meaningful on the anchor
    private @Nullable BlockPos anchorPos; // null means "I am the anchor"
    private @Nullable MapId cachedMapId;  // every member's own resilient copy, survives anchor destruction
    private int localU, localV, squareSize = 1;

    public MapDisplay(BlockPos pos, BlockState state) {
        super(FabulousBlockEntities.MAP_DISPLAY.get(), pos, state);
    }

    public boolean isAnchor() {
        return anchorPos == null;
    }

    public void makeAnchor(@Nullable MapId mapId, int localU, int localV, int squareSize) {
        this.anchorPos = null;
        this.mapId = mapId;
        this.cachedMapId = mapId;
        this.localU = localU;
        this.localV = localV;
        this.squareSize = squareSize;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public void makeChild(BlockPos anchorPos, @Nullable MapId cachedMapId, int localU, int localV, int squareSize) {
        this.anchorPos = anchorPos;
        this.mapId = null;
        this.cachedMapId = cachedMapId;
        this.localU = localU;
        this.localV = localV;
        this.squareSize = squareSize;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public @Nullable MapId resolveMapId() {
        return cachedMapId;
    }

    public int getLocalU() { return localU; }
    public int getLocalV() { return localV; }
    public int getSquareSize() { return squareSize; }
    public BlockPos getAnchorPos() {return anchorPos;}


    @Override
    protected void saveAdditional(CompoundTag output, HolderLookup.Provider registries) {
        super.saveAdditional(output, registries);
        output.storeNullable("mapId", MapId.CODEC, this.mapId);
        output.storeNullable("anchorPos", BlockPos.CODEC, this.anchorPos);
        output.storeNullable("cachedMapId", MapId.CODEC, this.cachedMapId);
        output.putInt("localU", localU);
        output.putInt("localV", localV);
        output.putInt("squareSize", squareSize);
    }

    @Override
    protected void loadAdditional(CompoundTag input, HolderLookup.Provider registries) {
        super.loadAdditional(input, registries);
        this.mapId = input.read("mapId", MapId.CODEC).orElse(null);
        this.anchorPos = input.read("anchorPos", BlockPos.CODEC).orElse(null);
        this.cachedMapId = input.read("cachedMapId", MapId.CODEC).orElse(null);
        this.localU = input.getIntOr("localU", 0);
        this.localV = input.getIntOr("localV", 0);
        this.squareSize = input.getIntOr("squareSize", 1);
    }
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}