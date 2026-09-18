package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class FabulousBlockTagProvider extends BlockTagsProvider {
    //define custom BlockTags here vvv
    public static final TagKey<Block> DESTROYABLE_BY_MACHETE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "destroyable_by_machete"));
    public static final TagKey<Block> VEGETATION = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "vegetation"));
    public static final TagKey<Block> SKILL_CALC_VEGETATION = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "skill_calc_vegitation"));
    public static final TagKey<Block> SKILL_CALC_ORES = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "skill_calc_ores"));
    public static final TagKey<Block> SKILL_CALC_MINERALS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "skill_calc_minerals"));
    public static final TagKey<Block> SKILL_CALC_LOGS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "skill_calc_logs"));
    public static final TagKey<Block> SKILL_CALC_CROPS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("fabulousadventures", "skill_calc_crops"));

    public FabulousBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FabulousAdventures.MODID,existingFileHelper);
    }
    //add Blocks to BlockTags here vvv
    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(BlockTags.ANVIL)
                .add(Blocks.DAMAGED_ANVIL.defaultBlockState().getBlock())
                .add(Blocks.CHIPPED_ANVIL.defaultBlockState().getBlock());
        this.tag(BlockTags.CLIMBABLE)
                .add(FabulousBlocks.ROPE_CLIMBABLE.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(FabulousBlocks.MAP_DISPLAY.get())
                .add(FabulousBlocks.OXYGEN_TANK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FabulousBlocks.MAP_DISPLAY.get())
                .add(FabulousBlocks.OXYGEN_TANK.get());
        this.tag(SKILL_CALC_ORES)
                .add(
                        Blocks.COAL_ORE.defaultBlockState().getBlock(),
                        Blocks.COPPER_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_COAL_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_IRON_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState().getBlock(),
                        Blocks.IRON_ORE.defaultBlockState().getBlock(),
                        Blocks.DIAMOND_ORE.defaultBlockState().getBlock(),
                        Blocks.LAPIS_ORE.defaultBlockState().getBlock(),
                        Blocks.EMERALD_ORE.defaultBlockState().getBlock(),
                        Blocks.GOLD_ORE.defaultBlockState().getBlock(),
                        Blocks.REDSTONE_ORE.defaultBlockState().getBlock(),
                        Blocks.NETHER_GOLD_ORE.defaultBlockState().getBlock(),
                        Blocks.NETHER_QUARTZ_ORE.defaultBlockState().getBlock(),
                        Blocks.ANCIENT_DEBRIS.defaultBlockState().getBlock()
                );
        this.tag(SKILL_CALC_MINERALS)
                .add(
                        Blocks.STONE.defaultBlockState().getBlock(),
                        Blocks.TUFF.defaultBlockState().getBlock(),
                        Blocks.DIORITE.defaultBlockState().getBlock(),
                        Blocks.GRANITE.defaultBlockState().getBlock(),
                        Blocks.ANDESITE.defaultBlockState().getBlock(),
                        Blocks.CALCITE.defaultBlockState().getBlock(),
                        Blocks.CLAY.defaultBlockState().getBlock(),
                        Blocks.BLACKSTONE.defaultBlockState().getBlock(),
                        Blocks.GRAVEL.defaultBlockState().getBlock(),
                        Blocks.MAGMA_BLOCK.defaultBlockState().getBlock(),
                        Blocks.DRIPSTONE_BLOCK.defaultBlockState().getBlock(),
                        Blocks.POINTED_DRIPSTONE.defaultBlockState().getBlock(),
                        Blocks.SOUL_SAND.defaultBlockState().getBlock(),
                        Blocks.SOUL_SOIL.defaultBlockState().getBlock(),
                        Blocks.DEEPSLATE.defaultBlockState().getBlock()
                        );
        this.tag(SKILL_CALC_CROPS)
                .addTag(BlockTags.CROPS);
        this.tag(SKILL_CALC_LOGS)
                .addTag(BlockTags.LOGS)
                .add(
                        Blocks.MANGROVE_ROOTS.defaultBlockState().getBlock(),
                        Blocks.MUDDY_MANGROVE_ROOTS.defaultBlockState().getBlock()
                );
        //vegetation blocks
        //noinspection unchecked
        this.tag(DESTROYABLE_BY_MACHETE)
                .addTags(
                        BlockTags.CAVE_VINES,
                        BlockTags.CROPS,
                        BlockTags.FLOWERS,
                        BlockTags.LEAVES
                ).add(Blocks.ACACIA_SAPLING.defaultBlockState().getBlock(),
                        Blocks.ATTACHED_MELON_STEM.defaultBlockState().getBlock(),
                        Blocks.ATTACHED_PUMPKIN_STEM.defaultBlockState().getBlock(),
                        Blocks.AZALEA.defaultBlockState().getBlock(),
                        Blocks.AZURE_BLUET.defaultBlockState().getBlock(),
                        Blocks.BAMBOO.defaultBlockState().getBlock(),
                        Blocks.BAMBOO_SAPLING.defaultBlockState().getBlock(),
                        Blocks.BIG_DRIPLEAF.defaultBlockState().getBlock(),
                        Blocks.BIG_DRIPLEAF_STEM.defaultBlockState().getBlock(),
                        Blocks.BIRCH_SAPLING.defaultBlockState().getBlock(),
                        Blocks.BROWN_MUSHROOM.defaultBlockState().getBlock(),
                        Blocks.CACTUS.defaultBlockState().getBlock(),
                        Blocks.CHERRY_SAPLING.defaultBlockState().getBlock(),
                        Blocks.COCOA.defaultBlockState().getBlock(),
                        Blocks.CRIMSON_FUNGUS.defaultBlockState().getBlock(),
                        Blocks.CRIMSON_ROOTS.defaultBlockState().getBlock(),
                        Blocks.DARK_OAK_SAPLING.defaultBlockState().getBlock(),
                        Blocks.FERN.defaultBlockState().getBlock(),
                        Blocks.FLOWERING_AZALEA.defaultBlockState().getBlock(),
                        Blocks.GLOW_LICHEN.defaultBlockState().getBlock(),
                        Blocks.HANGING_ROOTS.defaultBlockState().getBlock(),
                        Blocks.JUNGLE_SAPLING.defaultBlockState().getBlock(),
                        Blocks.KELP.defaultBlockState().getBlock(),
                        Blocks.KELP_PLANT.defaultBlockState().getBlock(),
                        Blocks.LARGE_FERN.defaultBlockState().getBlock(),
                        Blocks.LILY_PAD.defaultBlockState().getBlock(),
                        Blocks.MANGROVE_PROPAGULE.defaultBlockState().getBlock(),
                        Blocks.MANGROVE_ROOTS.defaultBlockState().getBlock(),
                        Blocks.MELON_STEM.defaultBlockState().getBlock(),
                        Blocks.MOSS_CARPET.defaultBlockState().getBlock(),
                        Blocks.OAK_SAPLING.defaultBlockState().getBlock(),
                        Blocks.PINK_PETALS.defaultBlockState().getBlock(),
                        Blocks.PUMPKIN_STEM.defaultBlockState().getBlock(),
                        Blocks.RED_MUSHROOM.defaultBlockState().getBlock(),
                        Blocks.SEA_PICKLE.defaultBlockState().getBlock(),
                        Blocks.SEAGRASS.defaultBlockState().getBlock(),
                        Blocks.SHORT_GRASS.defaultBlockState().getBlock(),
                        Blocks.SMALL_DRIPLEAF.defaultBlockState().getBlock(),
                        Blocks.SPORE_BLOSSOM.defaultBlockState().getBlock(),
                        Blocks.SPRUCE_SAPLING.defaultBlockState().getBlock(),
                        Blocks.SUGAR_CANE.defaultBlockState().getBlock(),
                        Blocks.SWEET_BERRY_BUSH.defaultBlockState().getBlock(),
                        Blocks.TALL_GRASS.defaultBlockState().getBlock(),
                        Blocks.TWISTING_VINES.defaultBlockState().getBlock(),
                        Blocks.TWISTING_VINES_PLANT.defaultBlockState().getBlock(),
                        Blocks.VINE.defaultBlockState().getBlock(),
                        Blocks.WARPED_FUNGUS.defaultBlockState().getBlock(),
                        Blocks.WARPED_ROOTS.defaultBlockState().getBlock(),
                        Blocks.WEEPING_VINES.defaultBlockState().getBlock(),
                        Blocks.WEEPING_VINES_PLANT.defaultBlockState().getBlock()
                );
        this.tag(VEGETATION)
                .addTag(DESTROYABLE_BY_MACHETE);

        this.tag(SKILL_CALC_VEGETATION)
                .addTag(VEGETATION);

    }

}