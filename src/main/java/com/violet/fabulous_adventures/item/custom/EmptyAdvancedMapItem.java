package com.violet.fabulous_adventures.item.custom;

import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EmptyMapItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;

public class EmptyAdvancedMapItem extends EmptyMapItem {
    public EmptyAdvancedMapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(!SkillUtils.isUnlocked(player,"advanced_map_unlock")){
            player.sendOverlayMessage(Component.literal("Advanced Map is not unlocked yet. Unlock it in the Skill tree (K)"));
            return InteractionResult.PASS;
        }
        ItemStack itemStack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            itemStack.consume(1, player);
            player.awardStat(Stats.ITEM_USED.get(this));
            serverLevel.playSound((Entity)null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundSource(), 1.0F, 1.0F);
            ItemStack map = AdvancedMapItem.create(serverLevel, player.getBlockX(), player.getBlockZ(), (byte)0, true, false);
            if (itemStack.isEmpty()) {
                return InteractionResult.SUCCESS.heldItemTransformedTo(map);
            } else {
                if (!player.getInventory().add(map.copy())) {
                    player.drop(map, false);
                }

                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.SUCCESS;
        }
    }
}
