package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.item.FabulousItems;
import com.violet.fabulous_adventures.item.custom.glider.GliderActive;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;

import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Collections;
import java.util.Optional;


public class FabulousModelProvider extends ModelProvider {

    public FabulousModelProvider(PackOutput output) {
        super(output, FabulousAdventures.MODID);
    }
//define texture slots here vvv
    public static final TextureSlot BASE = TextureSlot.create("rope", TextureSlot.ALL);
//define model templates here vvv
    @SuppressWarnings("deprecation")
    public static final ModelTemplate ROPE_TEMPLATE = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model")),
            Optional.of(""),
            BASE);
    public static final ModelTemplate ROPE_TEMPLATE_END = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model_end")),
            Optional.of(""),
            BASE);
//define model providers here vvv
    public static final TexturedModel.Provider ROPE_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            block -> new TextureMapping()
                    .put(BASE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE
    );
    public static final TexturedModel.Provider ROPE_END_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            block -> new TextureMapping()
                    .put(BASE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE_END);
//register block/item models here vvv
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
    //define model locations here vvv
        Identifier modelLoc_rope = ROPE_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE.get(), blockModels.modelOutput);
        Identifier modelLoc_rope_end = ROPE_END_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE_CLIMBABLE.get(), blockModels.modelOutput);
        Variant variant_rope = new Variant(modelLoc_rope);
    //register block models for blocks here vvv
        //rope model
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.ROPE.get(),
                                BlockModelGenerators.variant(variant_rope))
                        .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                .select(Direction.Axis.Z, BlockModelGenerators.X_ROT_90)
                                .select(Direction.Axis.X, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90)))
        );
        //rope climbable model
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.ROPE_CLIMBABLE.get(),
                                BlockModelGenerators.variant(variant_rope))
                        .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                .select(Direction.Axis.Z, BlockModelGenerators.X_ROT_90)
                                .select(Direction.Axis.X, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90)))
                        .with(PropertyDispatch.modify(RopeBlock.END)
                                .select(false, BlockModelGenerators.NOP)
                                .select(true, VariantMutator.MODEL.withValue(modelLoc_rope_end)))


        );
        //rope builder model (textureless cube)
        blockModels.createTrivialCube(FabulousBlocks.ROPE_BUILDER.get());
    //define item models for items here vvv
        //rope arrow model
        itemModels.generateFlatItem(FabulousItems.ROPE_ARROW.get(), ModelTemplates.FLAT_ITEM);
        //glider model (bool 3d model(dependent on GLiderActive() Data Component))
        itemModels.itemModelOutput.accept(
                FabulousItems.GLIDER.get(),
                new ConditionalItemModel.Unbaked(
                        Optional.empty(),
                        new GliderActive(),
                        new CuboidItemModelWrapper.Unbaked(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"item/glider_active"),Optional.empty(), Collections.emptyList()),
                        new CuboidItemModelWrapper.Unbaked(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"item/glider"),Optional.empty(), Collections.emptyList())

                )
        );
        //machete model
        itemModels.generateFlatItem(FabulousItems.MACHETE.get(),ModelTemplates.FLAT_ITEM);
    }

}
