package com.violet.fabulous_adventures.entity.custom;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class RopeArrow extends AbstractArrow {
    public RopeArrow(EntityType<RopeArrow> type, Level level) {
        super(type, level);
    }
    public RopeArrow(EntityType<RopeArrow> type, LivingEntity shooter, Level level, ItemStack pickupItem, ItemStack firedFromWeapon) {
        super(type, shooter, level, pickupItem, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return FabulousItems.ROPE_ARROW.toStack();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if (!level().getBlockState(hitResult.getBlockPos().above()).isAir()){
            level().setBlock(hitResult.getBlockPos().relative(hitResult.getDirection()), FabulousBlocks.ROPE_BUILDER.get().defaultBlockState(), Block.UPDATE_ALL);
            remove(RemovalReason.KILLED);

        }
    }
}
