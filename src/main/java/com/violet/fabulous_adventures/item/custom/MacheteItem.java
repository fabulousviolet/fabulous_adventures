package com.violet.fabulous_adventures.item.custom;

import com.violet.fabulous_adventures.datagen.FabulousBlockTagProvider;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MacheteItem extends Item {
    public static int AREA = 1;
    public MacheteItem(Properties properties) {
        super(properties);

    }

    //bigger hit sweep
    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target) {
        return target.getBoundingBox().inflate(1.3);
    }
    //rightclicking it on vegitation causes it to perform the mine action
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (playerHasBlockingItemUseIntent(context)) {
            return InteractionResult.PASS;


        } else {
            if (context.getLevel().getBlockState(context.getClickedPos()).is(FabulousBlockTagProvider.DESTROYABLE_BY_MACHETE))
                this.mineBlock(context.getItemInHand(), context.getLevel(), context.getLevel().getBlockState(context.getClickedPos()), context.getClickedPos(), context.getPlayer());
        }
        return InteractionResult.SUCCESS;
    }
    //destroys all vegitation blocks in a 3x3x3 scope around the originally broken block
    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        boolean toReturn = super.mineBlock(itemStack, level, state, pos, owner);
        itemStack.hurtAndBreak(1,owner,owner.getUsedItemHand());
        if (owner instanceof Player player){
            if (SkillUtils.isUnlocked(player, "machete_area")){
                AREA = 2;
            }
            if (SkillUtils.isUnlocked((Player) owner ,"machete_unlock")) {
                for (int deltaX = -AREA; deltaX <= AREA; deltaX++) {
                    for (int deltaY = -AREA; deltaY <= AREA; deltaY++) {
                        for (int deltaZ = -AREA; deltaZ <= AREA; deltaZ++) {
                            net.minecraft.core.BlockPos current = pos.relative(Direction.Axis.X, deltaX).relative(Direction.Axis.Y, deltaY).relative(Direction.Axis.Z, deltaZ);
                            if (level.getBlockState(current).is(FabulousBlockTagProvider.DESTROYABLE_BY_MACHETE)) {
                                if (level.isClientSide()) {
                                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                                } else {
                                    level.destroyBlock(current, false);
                                }
                            }
                        }
                    }
                }
            }else{
                ((Player) owner).displayClientMessage(Component.literal("Machete is not unlocked yet. Unlock it in the Skill tree (K)"),true);
            }
        }

        return toReturn;
    }

    //makes it prioritise the OffHand use
    private static boolean playerHasBlockingItemUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        return context.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS) && !player.isSecondaryUseActive();
    }
}


