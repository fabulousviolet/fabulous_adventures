package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class FabulousBlockTagProvider extends BlockTagsProvider {
    //define custom BlockTags here vvv
    public static final TagKey<Block> DESTROYABLE_BY_MACHETE = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "destroyable_by_machete"));
    public static final TagKey<Block> VEGETATION = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "vegetation"));
    public static final TagKey<Block> SKILL_CALC_VEGETATION = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "skill_calc_vegitation"));
    public static final TagKey<Block> SKILL_CALC_ORES = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "skill_calc_ores"));
    public static final TagKey<Block> SKILL_CALC_MINERALS = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "skill_calc_minerals"));
    public static final TagKey<Block> SKILL_CALC_LOGS = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "skill_calc_logs"));
    public static final TagKey<Block> SKILL_CALC_CROPS = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("fabulousadventures", "skill_calc_crops"));

    public FabulousBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FabulousAdventures.MODID);
    }
    //add Blocks to BlockTags here vvv
    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(BlockTags.SHEARS_MAJOR_BREAKING_SPEED)
                .add(FabulousBlocks.ROPE.getKey());
        this.tag(BlockTags.ANVIL)
                .add(Blocks.DAMAGED_ANVIL.builtInRegistryHolder().getKey())
                .add(Blocks.CHIPPED_ANVIL.builtInRegistryHolder().getKey());
        this.tag(BlockTags.CLIMBABLE)
                .add(FabulousBlocks.ROPE_CLIMBABLE.getKey());
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(FabulousBlocks.MAP_DISPLAY.getKey())
                .add(FabulousBlocks.OXYGEN_TANK.getKey());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FabulousBlocks.MAP_DISPLAY.getKey())
                .add(FabulousBlocks.OXYGEN_TANK.getKey());
        this.tag(SKILL_CALC_ORES)
                .addTag(BlockTags.ORES)
                .add(Blocks.ANCIENT_DEBRIS.builtInRegistryHolder().key());
        this.tag(SKILL_CALC_MINERALS)
                .add(
                        Blocks.STONE.builtInRegistryHolder().key(),
                        Blocks.TUFF.builtInRegistryHolder().key(),
                        Blocks.DIORITE.builtInRegistryHolder().key(),
                        Blocks.GRANITE.builtInRegistryHolder().key(),
                        Blocks.ANDESITE.builtInRegistryHolder().key(),
                        Blocks.CALCITE.builtInRegistryHolder().key(),
                        Blocks.CINNABAR.builtInRegistryHolder().key(),
                        Blocks.CLAY.builtInRegistryHolder().key(),
                        Blocks.BLACKSTONE.builtInRegistryHolder().key(),
                        Blocks.GRAVEL.builtInRegistryHolder().key(),
                        Blocks.MAGMA_BLOCK.builtInRegistryHolder().key(),
                        Blocks.SULFUR.builtInRegistryHolder().key(),
                        Blocks.DRIPSTONE_BLOCK.builtInRegistryHolder().key(),
                        Blocks.POINTED_DRIPSTONE.builtInRegistryHolder().key(),
                        Blocks.SULFUR_SPIKE.builtInRegistryHolder().key(),
                        Blocks.SOUL_SAND.builtInRegistryHolder().key(),
                        Blocks.SOUL_SOIL.builtInRegistryHolder().key(),
                        Blocks.DEEPSLATE.builtInRegistryHolder().key()
                        );
        this.tag(SKILL_CALC_CROPS)
                .addTag(BlockTags.CROPS);
        this.tag(SKILL_CALC_LOGS)
                .addTag(BlockTags.LOGS)
                .add(
                        Blocks.MANGROVE_ROOTS.builtInRegistryHolder().key(),
                        Blocks.MUDDY_MANGROVE_ROOTS.builtInRegistryHolder().key()
                );
        //vegetation blocks
        //noinspection unchecked
        this.tag(VEGETATION)
                .addTags(
                        BlockTags.CAVE_VINES,
                        BlockTags.CROPS,
                        BlockTags.FLOWERS,
                        BlockTags.LEAVES
                ).add(Blocks.ACACIA_SAPLING.builtInRegistryHolder().key(),
                        Blocks.ATTACHED_MELON_STEM.builtInRegistryHolder().key(),
                        Blocks.ATTACHED_PUMPKIN_STEM.builtInRegistryHolder().key(),
                        Blocks.AZALEA.builtInRegistryHolder().key(),
                        Blocks.AZURE_BLUET.builtInRegistryHolder().key(),
                        Blocks.BAMBOO.builtInRegistryHolder().key(),
                        Blocks.BAMBOO_SAPLING.builtInRegistryHolder().key(),
                        Blocks.BIG_DRIPLEAF.builtInRegistryHolder().key(),
                        Blocks.BIG_DRIPLEAF_STEM.builtInRegistryHolder().key(),
                        Blocks.BIRCH_SAPLING.builtInRegistryHolder().key(),
                        Blocks.BROWN_MUSHROOM.builtInRegistryHolder().key(),
                        Blocks.CACTUS.builtInRegistryHolder().key(),
                        Blocks.CACTUS_FLOWER.builtInRegistryHolder().key(),
                        Blocks.CHERRY_SAPLING.builtInRegistryHolder().key(),
                        Blocks.COCOA.builtInRegistryHolder().key(),
                        Blocks.CRIMSON_FUNGUS.builtInRegistryHolder().key(),
                        Blocks.CRIMSON_ROOTS.builtInRegistryHolder().key(),
                        Blocks.DARK_OAK_SAPLING.builtInRegistryHolder().key(),
                        Blocks.FERN.builtInRegistryHolder().key(),
                        Blocks.FIREFLY_BUSH.builtInRegistryHolder().key(),
                        Blocks.BUSH.builtInRegistryHolder().key(),
                        Blocks.FLOWERING_AZALEA.builtInRegistryHolder().key(),
                        Blocks.GLOW_LICHEN.builtInRegistryHolder().key(),
                        Blocks.HANGING_ROOTS.builtInRegistryHolder().key(),
                        Blocks.JUNGLE_SAPLING.builtInRegistryHolder().key(),
                        Blocks.KELP.builtInRegistryHolder().key(),
                        Blocks.KELP_PLANT.builtInRegistryHolder().key(),
                        Blocks.LARGE_FERN.builtInRegistryHolder().key(),
                        Blocks.LEAF_LITTER.builtInRegistryHolder().key(),
                        Blocks.LILY_PAD.builtInRegistryHolder().key(),
                        Blocks.MANGROVE_PROPAGULE.builtInRegistryHolder().key(),
                        Blocks.MANGROVE_ROOTS.builtInRegistryHolder().key(),
                        Blocks.MELON_STEM.builtInRegistryHolder().key(),
                        Blocks.MOSS_CARPET.builtInRegistryHolder().key(),
                        Blocks.OAK_SAPLING.builtInRegistryHolder().key(),
                        Blocks.PALE_HANGING_MOSS.builtInRegistryHolder().key(),
                        Blocks.PALE_MOSS_CARPET.builtInRegistryHolder().key(),
                        Blocks.PALE_OAK_SAPLING.builtInRegistryHolder().key(),
                        Blocks.PINK_PETALS.builtInRegistryHolder().key(),
                        Blocks.PUMPKIN_STEM.builtInRegistryHolder().key(),
                        Blocks.RED_MUSHROOM.builtInRegistryHolder().key(),
                        Blocks.RED_SHRUB.builtInRegistryHolder().key(),
                        Blocks.SEA_PICKLE.builtInRegistryHolder().key(),
                        Blocks.SEAGRASS.builtInRegistryHolder().key(),
                        Blocks.SHORT_GRASS.builtInRegistryHolder().key(),
                        Blocks.SMALL_DRIPLEAF.builtInRegistryHolder().key(),
                        Blocks.SPORE_BLOSSOM.builtInRegistryHolder().key(),
                        Blocks.SPRUCE_SAPLING.builtInRegistryHolder().key(),
                        Blocks.SUGAR_CANE.builtInRegistryHolder().key(),
                        Blocks.SHELF_MUSHROOM.builtInRegistryHolder().key(),
                        Blocks.SWEET_BERRY_BUSH.builtInRegistryHolder().key(),
                        Blocks.TALL_DRY_GRASS.builtInRegistryHolder().key(),
                        Blocks.TALL_GRASS.builtInRegistryHolder().key(),
                        Blocks.TWISTING_VINES.builtInRegistryHolder().key(),
                        Blocks.TWISTING_VINES_PLANT.builtInRegistryHolder().key(),
                        Blocks.VINE.builtInRegistryHolder().key(),
                        Blocks.WARPED_FUNGUS.builtInRegistryHolder().key(),
                        Blocks.WARPED_ROOTS.builtInRegistryHolder().key(),
                        Blocks.WEEPING_VINES.builtInRegistryHolder().key(),
                        Blocks.WEEPING_VINES_PLANT.builtInRegistryHolder().key()
                );
        this.tag(DESTROYABLE_BY_MACHETE)
                .addTag(VEGETATION);

        this.tag(SKILL_CALC_VEGETATION)
                .addTag(VEGETATION);

    }

}