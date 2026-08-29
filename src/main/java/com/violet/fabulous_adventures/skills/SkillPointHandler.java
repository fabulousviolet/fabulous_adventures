package com.violet.fabulous_adventures.skills;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.datagen.FabulousBlockTagProvider;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkillnodeDef;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class SkillPointHandler {

    private static final Set<TagKey<Block>> SKILL_MINED_CALC_TAGS = Set.of(
            FabulousBlockTagProvider.SKILL_CALC_CROPS,
            FabulousBlockTagProvider.SKILL_CALC_LOGS,
            FabulousBlockTagProvider.SKILL_CALC_ORES,
            FabulousBlockTagProvider.SKILL_CALC_MINERALS,
            FabulousBlockTagProvider.SKILL_CALC_VEGETATION
    );
    private static final Map<MobCategory, Integer> SKILL_KILLED_CALC_CATEGORIES = Map.of(
            MobCategory.CREATURE, 1,
            MobCategory.MONSTER, 3,
            MobCategory.AMBIENT, 1,
            MobCategory.UNDERGROUND_WATER_CREATURE, 2,
            MobCategory.WATER_AMBIENT, 1,
            MobCategory.WATER_CREATURE, 1
    );

    private static final Map<Identifier,Double> SKILL_TRAVEL_CALC_STATS = Map.of(
            Stats.WALK_ONE_CM, 0.0001,
            Stats.WALK_UNDER_WATER_ONE_CM, 0.001,
            Stats.SWIM_ONE_CM, 0.0001,
            Stats.JUMP, 0.1,
            Stats.WALK_ON_WATER_ONE_CM, 0.001,
            Stats.CROUCH_ONE_CM,0.001,
            Stats.CLIMB_ONE_CM, 0.001,
            Stats.SPRINT_ONE_CM, 0.0001,
            Stats.FLY_ONE_CM,0.00001
    );
    private static final Set<Integer> MINED_MILESTONES = Set.of(
            50,
            100,
            500,
            1000,
            2000,
            5000,
            10000
    );

    private static final Set<Integer> KILLED_MILESTONES = Set.of(
            20,
            50,
            100,
            200,
            500,
            1000,
            2500
    );

    private static final Set<Integer> TRAVELED_MILESTONES = Set.of(
            10,
            20,
            50,
            100,
            200,
            500,
            1000
    );
    private static final int RECALC_INTERVAL_TICKS = 100;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % RECALC_INTERVAL_TICKS != 0) return;
        int pointsBeforeTick = player.getData(FabulousAttachments.SKILL_POINTS.get());
        int pointsAfterTick = calculateSkillPoints(player);
        if (pointsBeforeTick < pointsAfterTick) {
            player.setData(FabulousAttachments.SKILL_POINTS.get(), pointsAfterTick);
            event.getEntity().sendOverlayMessage(Component.literal("New skill point(s) unlocked. Press 'K' to open the skill tree."));
        } else if (pointsBeforeTick != pointsAfterTick) {
            player.setData(FabulousAttachments.SKILL_POINTS.get(), pointsAfterTick);
        }
    }

    private static int calculateSkillPoints(ServerPlayer player) {
        int spentPoints = 0;
        for (SkillnodeDef node : SkilltreeScreen.NODES) {
            if (player.getData(FabulousAttachments.UNLOCKED_SKILLS.get()).contains(node.id())) {
                spentPoints = spentPoints + node.cost();
            }
        }
        int awardedPoints = calculateMinedPoints(player) + calculateKillPoints(player) + calculateTravelPoints(player) /*calculateUsePoints(player)*/;
        return awardedPoints - spentPoints;
    }


    private static int calculateMinedPoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        //for every tag add the mined count of all the blocks contained in the tag
        for (TagKey<Block> tag : SKILL_MINED_CALC_TAGS) {
            int tag_mined_sum = 0;
            for (Block block : BuiltInRegistries.BLOCK) {
                int minedCount = stats.getValue(Stats.BLOCK_MINED.get(block));
                if (minedCount == 0) continue;
                if (block.builtInRegistryHolder().is(tag)) {
                    tag_mined_sum = tag_mined_sum + minedCount;
                }
            }
            //for every tag check if the sum of mined blocks per tag surpasses a milestone and award a skill point to return
            for (int milestone : MINED_MILESTONES) {
                if (tag_mined_sum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }

    private static int calculateKillPoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        for (MobCategory category : SKILL_KILLED_CALC_CATEGORIES.keySet()) {
            int killedSum = 0;
            for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE){
                if (category == type.getCategory()){
                    killedSum = killedSum + SKILL_KILLED_CALC_CATEGORIES.get(category) * stats.getValue(Stats.ENTITY_KILLED.get(type));
                }
            }
            for (int milestone : KILLED_MILESTONES) {
                if (killedSum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }

    private static int calculateTravelPoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        for (Identifier stat : SKILL_TRAVEL_CALC_STATS.keySet()) {
            double travelledSum = stats.getValue(Stats.CUSTOM.get(stat)) * SKILL_TRAVEL_CALC_STATS.get(stat);
            for (int milestone : TRAVELED_MILESTONES) {
                if (travelledSum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }
}



