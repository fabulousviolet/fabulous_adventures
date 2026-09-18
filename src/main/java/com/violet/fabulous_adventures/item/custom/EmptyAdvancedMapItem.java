package com.violet.fabulous_adventures.item.custom;

import com.violet.fabulous_adventures.item.custom.advanced_map.AdvancedMapItem;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EmptyMapItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EmptyAdvancedMapItem extends EmptyMapItem {
    public EmptyAdvancedMapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!SkillUtils.isUnlocked(player,"advanced_map_unlock")){
            player.displayClientMessage(Component.literal("Advanced Map is not unlocked yet. Unlock it in the Skill tree (K)"),true);
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        ItemStack itemStack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            itemStack.consume(1, player);
            player.awardStat(Stats.ITEM_USED.get(this));
            serverLevel.playSound((Player)null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundSource(), 1.0F, 1.0F);
            ItemStack map = AdvancedMapItem.create(serverLevel, player.getBlockX(), player.getBlockZ(), (byte)0, true, false);
            if (itemStack.isEmpty()) {
                return InteractionResultHolder.success(player.getItemInHand(hand));
            } else {
                if (!player.getInventory().add(map.copy())) {
                    player.drop(map, false);
                }

                return InteractionResultHolder.success(itemStack);
            }
        } else {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
    }
}
