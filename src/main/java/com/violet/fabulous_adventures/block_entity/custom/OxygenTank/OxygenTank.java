package com.violet.fabulous_adventures.block_entity.custom.OxygenTank;

import com.violet.fabulous_adventures.block_entity.FabulousBlockEntities;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class OxygenTank extends BlockEntity {

    private int value = 0;
    private static final int MAX_VALUE = 2000;
    private static final double RANGE = 2.0;
    private static final int REFILL_TICK_INTERVAL_ON_LAND = 10;
    private static final int DRAIN_TICK_INTERVAL = 20;
    private static final int PARTICLE_INTERVAL = 5;
    private static final int PARTICLES_PER_SPAWN = 2;

    public OxygenTank(BlockPos worldPosition, BlockState blockState) {
        super(FabulousBlockEntities.OXYGEN_TANK.get(), worldPosition, blockState);
    }

    public int getFillValue() { return value; }
    public static int getMaxValue() { return MAX_VALUE; }

    public static void tick(Level level, BlockPos pos, BlockState state, OxygenTank tank) {
        if (level.isClientSide()) return;

        boolean surroundedByNonWater = true;
        for (Direction dir : Direction.values()) {
            if (level.getFluidState(pos.relative(dir)).is(FluidTags.WATER)) {
                surroundedByNonWater = false;
                break;
            }
        }

        AABB range = new AABB(pos).inflate(RANGE);
        List<Player> playersNeedingAir = level.getEntitiesOfClass(Player.class, range,
                p -> p.getAirSupply() < p.getMaxAirSupply());

        if (!playersNeedingAir.isEmpty() && tank.value > 0) {
            if (level.getGameTime() % DRAIN_TICK_INTERVAL == 0) {
                for (Player player : playersNeedingAir) {
                    if (tank.value <= 0) break;
                    player.setAirSupply(Math.min(player.getMaxAirSupply(), player.getAirSupply() + 40));
                    tank.value = Math.max(0, tank.value - 20);
                }
                tank.setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                }
            }
        }
        if (surroundedByNonWater && tank.value < MAX_VALUE) {
            if (level.getGameTime() % REFILL_TICK_INTERVAL_ON_LAND == 0) {
                tank.value = Math.min(MAX_VALUE, tank.value + 20);
                tank.setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                }
            }
        }
        if (!surroundedByNonWater && tank.value > 0 &&level instanceof ServerLevel serverLevel) {
            spawnAmbientBubbles(serverLevel, pos);
        }

    }

    private static void spawnAmbientBubbles(ServerLevel level, BlockPos pos) {
        if (level.getGameTime() % PARTICLE_INTERVAL != 0) return;

        RandomSource random = level.getRandom();

        for (int i = 0; i < PARTICLES_PER_SPAWN; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble() * RANGE;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double offsetY = random.nextDouble() * 1.5;

            double x = pos.getX() + 0.5 + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + 0.5 + offsetZ;

            level.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z, 1, 0.0, 0.02, 0.0, 0.01);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("value", value);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.value = input.getIntOr("value", 0);
    }


    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(FabulousDataComponents.OXYGEN_TANK_VALUE.get(), this.value);
    }

    @Override
    public void applyImplicitComponents(DataComponentGetter componentGetter) {
        super.applyImplicitComponents(componentGetter);
        this.value = componentGetter.getOrDefault(FabulousDataComponents.OXYGEN_TANK_VALUE.get(), 0);
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



