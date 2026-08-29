package com.violet.fabulous_adventures.entity.custom;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.item.FabulousItems;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public class RopeArrow extends AbstractArrow {
    public RopeArrow(EntityType<RopeArrow> type, Level level) {
        super(type, level);
    }
    public RopeArrow(EntityType<RopeArrow> type, LivingEntity shooter, Level level, ItemStack pickupItem, ItemStack firedFromWeapon) {
        super(type, shooter, level, pickupItem, firedFromWeapon);
    }
//define what item to give when picked up
    @Override
    protected @NonNull ItemStack getDefaultPickupItem() {
        return FabulousItems.ROPE_ARROW.toStack();
    }
//place a rope builder block when hitting a valid place position and delete the arrow
    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if (getOwner() instanceof Player player){
            if(!SkillUtils.isUnlocked(player,"rope_arrow_unlock")){
                player.sendOverlayMessage(Component.literal("Rope Arrows are not unlocked yet. Unlock them in the Skill tree (K)"));
                return;
            }
        }

        if (!level().getBlockState(hitResult.getBlockPos().above()).isAir()){
            level().setBlock(hitResult.getBlockPos().relative(hitResult.getDirection()), FabulousBlocks.ROPE_BUILDER.get().defaultBlockState(), Block.UPDATE_ALL);
            remove(RemovalReason.KILLED);

        }
    }
}
