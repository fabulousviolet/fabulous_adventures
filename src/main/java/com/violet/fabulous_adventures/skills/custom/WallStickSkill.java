package com.violet.fabulous_adventures.skills.custom;

import com.violet.fabulous_adventures.skills.Skill;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallStickSkill extends Skill {

    @Override
    public void tick(Player player, boolean unlocked) {
        if (!unlocked) return;

        boolean touchingWall = player.isShiftKeyDown() && isNextToWall(player, 0.2);
        Vec3 motion = player.getDeltaMovement();
        if (touchingWall) {
            player.setDeltaMovement(motion.x, -0.01, motion.z);
            player.resetFallDistance();
        }
    }

    private boolean isNextToWall(Player player, double margin) {
        AABB checkBox = player.getBoundingBox().inflate(margin, 0, margin);
        BlockPos min = BlockPos.containing(checkBox.minX, checkBox.minY, checkBox.minZ);
        BlockPos max = BlockPos.containing(checkBox.maxX, checkBox.maxY, checkBox.maxZ);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = player.level().getBlockState(pos);
            if (state.isAir()) continue;

            VoxelShape shape = state.getCollisionShape(player.level(), pos);
            if (shape.isEmpty()) continue;

            AABB blockBox = shape.bounds().move(pos.getX(), pos.getY(), pos.getZ());
            if (checkBox.intersects(blockBox)) {
                return true;
            }
        }
        return false;
    }
}


