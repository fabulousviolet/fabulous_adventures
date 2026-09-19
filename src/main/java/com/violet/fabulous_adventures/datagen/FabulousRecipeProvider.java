package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;

import java.util.Set;

public class FabulousRecipeProvider extends RecipeProvider {
    public FabulousRecipeProvider(
            BootstrapContext<Recipe<?>> recipeOutput,
            BootstrapContext<Advancement> advancementOutput
    ) {
        super(recipeOutput, advancementOutput);
    }
    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                new FabulousRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }
    String modId = FabulousAdventures.MODID+":";
    @Override
    protected void buildRecipes() {
        this.shaped(RecipeCategory.DECORATIONS, FabulousBlocks.ROPE.asItem(),4)
                .pattern("A")
                .pattern("B")
                .pattern("A")
                .define('A', Items.STRING)
                .define('B',ItemTags.WOOL)
                .unlockedBy("has_wool",this.has(ItemTags.WOOL))
                .save(output,modId+"craft_rope")
        ;
        this.shaped(RecipeCategory.REDSTONE, FabulousBlocks.MAP_DISPLAY.asItem(),2)
                .pattern("AAA")
                .pattern("BCB")
                .define('A', Items.PAPER)
                .define('B',ItemTags.PLANKS)
                .define('C',Items.REDSTONE)
                .unlockedBy(getHasName(FabulousItems.ADVANCED_MAP),this.has(FabulousItems.EMPTY_ADVANCED_MAP))
                .save(output,modId+"craft_map_display")
        ;
        this.shaped(RecipeCategory.REDSTONE, FabulousBlocks.OXYGEN_TANK.asItem())
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B',Items.IRON_BLOCK)
                .define('C',Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(Items.GLASS),this.has(Tags.Items.GLASS_BLOCKS))
                .save(output,modId+"craft_oxygen_tank")
        ;
        this.shaped(RecipeCategory.TOOLS, FabulousItems.GLIDER)
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

        this.shapeless(RecipeCategory.COMBAT,FabulousItems.ROPE_ARROW)
                .requires(Items.ARROW)
                .requires(FabulousBlocks.ROPE.asItem())
                .unlockedBy(getHasName(FabulousBlocks.ROPE), this.has(FabulousBlocks.ROPE))
                .save(output,modId+"craft_rope_arrow")
        ;
        this.shapeless(RecipeCategory.TOOLS,FabulousItems.MACHETE)
                        .requires(Items.IRON_SWORD)
                        .requires(Items.LEATHER)
                        .requires(Items.IRON_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT))
                        .save(output,modId+"craft_machete")
        ;
        this.shapeless(RecipeCategory.MISC,FabulousItems.EMPTY_ADVANCED_MAP)
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
