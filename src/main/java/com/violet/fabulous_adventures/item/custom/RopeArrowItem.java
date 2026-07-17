package com.violet.fabulous_adventures.item.custom;

import com.violet.fabulous_adventures.entity.FabulousEntities;
import com.violet.fabulous_adventures.entity.custom.RopeArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class RopeArrowItem extends ArrowItem {
    public RopeArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
        return new RopeArrow(FabulousEntities.ROPE_ARROW.get(), owner,level,itemStack,firedFromWeapon);
    }
}
