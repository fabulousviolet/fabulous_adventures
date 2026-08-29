package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class FabulousRecipeProvider extends RecipeProvider {
    public FabulousRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new FabulousRecipeProvider(provider,recipeOutput);
        }

        @Override
        public String getName() {
            return "fabulous_recipes";
        }
    }
    String modId = FabulousAdventures.MODID+":";
    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.DECORATIONS, FabulousBlocks.ROPE.asItem(),4)
                .pattern("A")
                .pattern("B")
                .pattern("A")
                .define('A', Items.STRING)
                .define('B',ItemTags.WOOL)
                .unlockedBy("has_wool",this.has(ItemTags.WOOL))
                .save(output,modId+"craft_rope")
        ;
        shaped(RecipeCategory.REDSTONE, FabulousBlocks.MAP_DISPLAY.asItem(),2)
                .pattern("AAA")
                .pattern("BCB")
                .define('A', Items.PAPER)
                .define('B',ItemTags.PLANKS)
                .define('C',Items.REDSTONE)
                .unlockedBy(getHasName(FabulousItems.ADVANCED_MAP),this.has(FabulousItems.EMPTY_ADVANCED_MAP))
                .save(output,modId+"craft_map_display")
        ;
        shaped(RecipeCategory.REDSTONE, FabulousBlocks.OXYGEN_TANK.asItem())
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B',Items.IRON_BLOCK)
                .define('C',Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(Items.GLASS),this.has(Tags.Items.GLASS_BLOCKS))
                .save(output,modId+"craft_oxygen_tank")
        ;
        shaped(RecipeCategory.TOOLS, FabulousItems.GLIDER)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("D D")
                .define('A', ItemTags.WOOL)
                .define('B',FabulousBlocks.ROPE.asItem())
                .define('C',Items.STICK)
                .define('D',Items.IRON_INGOT)
                .unlockedBy(getHasName(FabulousBlocks.ROPE),this.has(FabulousBlocks.ROPE))
                .save(output,modId+"craft_glider")
        ;

        shapeless(RecipeCategory.COMBAT,FabulousItems.ROPE_ARROW)
                .requires(Items.ARROW)
                .requires(FabulousBlocks.ROPE.asItem())
                .unlockedBy(getHasName(FabulousBlocks.ROPE), this.has(FabulousBlocks.ROPE))
                .save(output,modId+"craft_rope_arrow")
        ;
        shapeless(RecipeCategory.TOOLS,FabulousItems.MACHETE)
                        .requires(Items.IRON_SWORD)
                        .requires(Items.LEATHER)
                        .requires(Items.IRON_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT))
                        .save(output,modId+"craft_machete")
        ;
        shapeless(RecipeCategory.MISC,FabulousItems.EMPTY_ADVANCED_MAP)
                        .requires(Items.MAP)
                        .requires(Items.DIAMOND)
                        .unlockedBy(getHasName(Items.MAP), this.has(Items.MAP))
                        .save(output,modId+"craft_empty_advanced_map")
        ;

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(Items.HEAVY_CORE),
                Ingredient.of(Items.DIAMOND),
                RecipeCategory.COMBAT,
                FabulousItems.CLAYMORE.asItem()
                )
                .unlocks(getHasName(Items.HEAVY_CORE),this.has(Items.HEAVY_CORE))
                .save(output,modId+"upgrade_to_claymore");
    }
}
