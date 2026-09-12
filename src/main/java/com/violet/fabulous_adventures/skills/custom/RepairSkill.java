package com.violet.fabulous_adventures.skills.custom;

import com.violet.fabulous_adventures.datagen.FabulousItemTagProvider;
import com.violet.fabulous_adventures.skills.Skill;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class RepairSkill{
    private static final int XP_COST = 15;
    private static final int REPAIR_AMOUNT = 100;
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if(event.getLevel().isClientSide()
                || event.getHand() != InteractionHand.MAIN_HAND
                ||!player.isShiftKeyDown()
                ||!SkillUtils.isUnlocked(player,"repair_skill")) {return;
        }
        if (!event.getLevel().getBlockState(event.getPos()).is(BlockTags.ANVIL)) {

        }
        ItemStack stack = player.getMainHandItem();
        if (!stack.isDamageableItem()
                || !stack.isDamaged()
                || player.totalExperience < XP_COST) {
            return;
        }
        int repairedDamage = Math.max(0, stack.getDamageValue() - REPAIR_AMOUNT);
        stack.setDamageValue(repairedDamage);
        player.giveExperiencePoints(-XP_COST);
        event.setCanceled(true);
    }
}
