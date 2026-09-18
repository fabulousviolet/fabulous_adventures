package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.datagen.FabulousBlockTagProvider;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkillnodeDef;
import com.violet.fabulous_adventures.menus.custom.skilltree.SkilltreeScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkillProgressCalculator {
    private static final Set<TagKey<Block>> SKILL_MINED_CALC_TAGS = Set.of(
            FabulousBlockTagProvider.SKILL_CALC_CROPS,
            FabulousBlockTagProvider.SKILL_CALC_LOGS,
            FabulousBlockTagProvider.SKILL_CALC_ORES,
            FabulousBlockTagProvider.SKILL_CALC_MINERALS,
            FabulousBlockTagProvider.SKILL_CALC_VEGETATION
    );
    private static final Map<MobCategory, Double> SKILL_KILLED_CALC_CATEGORIES = Map.of(
            MobCategory.CREATURE, 1.0,
            MobCategory.MONSTER, 0.5,
            MobCategory.WATER_AMBIENT, 1.0,
            MobCategory.WATER_CREATURE, 0.75
    );

    private static final Map<ResourceLocation, Double> SKILL_TRAVEL_CALC_STATS = Map.of(
            Stats.WALK_ONE_CM, 0.0001,
            Stats.WALK_UNDER_WATER_ONE_CM, 0.001,
            Stats.SWIM_ONE_CM, 0.0001,
            Stats.JUMP, 0.1,
            Stats.CROUCH_ONE_CM, 0.001,
            Stats.CLIMB_ONE_CM, 0.001,
            Stats.SPRINT_ONE_CM, 0.0001,
            Stats.FLY_ONE_CM, 0.00001
    );

    private static final Map<TagKey<Item>, Double> SKILL_USE_CALC_STATS = Map.of(
            Tags.Items.TOOLS, 0.1,
            Tags.Items.CROPS, 0.1,
            Tags.Items.FOODS, 0.05,
            Tags.Items.DYES, 0.5
    );
    private static final Set<Integer> MINED_MILESTONES = Set.of(
            50,
            100,
            500,
            1000,
            2000,
            5000,
            10000,
            20000,
            50000
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
            1000,
            2000,
            5000
    );
    private static final Set<Integer> USED_MILESTONES = Set.of(
            10,
            20,
            50,
            100,
            200,
            500,
            1000
    );

    public static int calculateSkillPoints(ServerPlayer player) {
        int spentPoints = 0;
        for (SkillnodeDef node : SkilltreeScreen.NODES) {
            if (player.getData(FabulousAttachments.UNLOCKED_SKILLS.get()).contains(node.id())) {
                spentPoints = spentPoints + node.cost();
            }
        }
        int awardedPoints = calculateMinedPoints(player) + calculateKillPoints(player) + calculateTravelPoints(player) + calculateUsePoints(player);
        return awardedPoints - spentPoints + player.getData(FabulousAttachments.BASE_SKILL_POINTS);
    }


    public static int calculateMinedPoints(ServerPlayer player) {
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

    public static int calculateKillPoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        for (MobCategory category : SKILL_KILLED_CALC_CATEGORIES.keySet()) {
            double killedSum = 0;
            for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
                if (category == type.getCategory()) {
                    killedSum = killedSum + SKILL_KILLED_CALC_CATEGORIES.get(category) * stats.getValue(Stats.ENTITY_KILLED.get(type));
                }
            }
            for (int milestone : KILLED_MILESTONES) {
                if (killedSum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }

    public static int calculateTravelPoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        for (ResourceLocation stat : SKILL_TRAVEL_CALC_STATS.keySet()) {
            double travelledSum = stats.getValue(Stats.CUSTOM.get(stat)) * SKILL_TRAVEL_CALC_STATS.get(stat);
            for (int milestone : TRAVELED_MILESTONES) {
                if (travelledSum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }

    public static int calculateUsePoints(ServerPlayer player) {
        StatsCounter stats = player.getStats();
        int totalPoints = 0;
        for (TagKey<Item> tag : SKILL_USE_CALC_STATS.keySet()) {
            double usedSum = 0;
            for (Item item : BuiltInRegistries.ITEM) {
                double usedCount = stats.getValue(Stats.ITEM_USED.get(item));
                if (usedCount == 0) continue;
                if (item.builtInRegistryHolder().is(tag)) {
                    usedSum = usedSum + usedCount * SKILL_USE_CALC_STATS.get(tag);
                }

            }
            for (int milestone : USED_MILESTONES) {
                if (usedSum >= milestone) totalPoints++;
            }
        }
        return totalPoints;
    }

    public static double getRawUsedValue(LocalPlayer player, TagKey<Item> tag) {
        StatsCounter stats = player.getStats();
        double sum = 0;
        for (Item item : BuiltInRegistries.ITEM) {
            if (item.builtInRegistryHolder().is(tag)) {
                sum += stats.getValue(Stats.ITEM_USED.get(item));
            }
        }
        return sum;
    }

    public static double getRawTraveledValue(LocalPlayer player, ResourceLocation stat) {
        return player.getStats().getValue(Stats.CUSTOM.get(stat));
    }

    public static double getRawKilledValue(LocalPlayer player, MobCategory category) {
        StatsCounter stats = player.getStats();
        double sum = 0;
        for (EntityType entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType.getCategory() == category) {
                sum += stats.getValue(Stats.ENTITY_KILLED.get(entityType));
            }
        }
        return sum;
    }

    public static double getRawMinedValue(LocalPlayer player, TagKey<Block> tag) {
        StatsCounter stats = player.getStats();
        double sum = 0;
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block.builtInRegistryHolder().is(tag)) {
                sum += stats.getValue(Stats.BLOCK_MINED.get(block));
            }
        }
        return sum;
    }

    public static MilestoneProgress computeProgress(double rawValue, Set<Integer> milestones, double weight) {
        List<Integer> sorted = milestones.stream().sorted().toList();

        for (int milestone : sorted) {
            double effectiveMilestone = milestone / weight;
            if (rawValue < effectiveMilestone) {
                return new MilestoneProgress(rawValue, effectiveMilestone);
            }
        }

        double lastMilestone = sorted.isEmpty() ? 0 : sorted.get(sorted.size() - 1) / weight;
        return new MilestoneProgress(rawValue, lastMilestone);
    }


    public static double getWeightFor(SkillCriterionType type, String id) {
        return switch (type) {
            case MINED -> 1.0; // MINED has no weight map in your current design
            case USED -> SKILL_USE_CALC_STATS.getOrDefault(TagKey.create(Registries.ITEM, ResourceLocation.parse(id)), 1.0);
            case KILLED -> SKILL_KILLED_CALC_CATEGORIES.getOrDefault(MobCategory.valueOf(id.toUpperCase()), 1.0);
            case TRAVELED -> {
                ResourceLocation stat = ResourceLocation.tryParse(id);
                yield stat == null ? 1.0 : SKILL_TRAVEL_CALC_STATS.getOrDefault(stat, 1.0);
            }
        };
    }
    public static double getRawValue(LocalPlayer player, SkillCriterionType type, String id) {
        return switch (type) {
            case MINED -> getRawMinedValue(player, TagKey.create(Registries.BLOCK, ResourceLocation.parse(id)));
            case USED -> getRawUsedValue(player, TagKey.create(Registries.ITEM, ResourceLocation.parse(id)));
            case KILLED -> getRawKilledValue(player, MobCategory.valueOf(id.toUpperCase()));
            case TRAVELED -> {
                ResourceLocation statId = ResourceLocation.tryParse(id);
                if (statId == null) {
                    yield 0.0;
                }

                ResourceLocation registeredStat = BuiltInRegistries.CUSTOM_STAT.get(statId);
                yield registeredStat == null ? 0.0 : getRawTraveledValue(player, registeredStat);
            }
        };
    }
    public static Set<Integer> getMilestonesFor(SkillCriterionType type) {
        switch (type){
            case TRAVELED -> {
                return TRAVELED_MILESTONES;
            }
            case USED -> {
                return USED_MILESTONES;
            }
            case KILLED -> {
                return KILLED_MILESTONES;
            }
            case MINED -> {
                return MINED_MILESTONES;
            }
            default -> {
                Set<Integer> set = new HashSet<>();
                set.add(0);
                return set;
            }
        }
    }
}
