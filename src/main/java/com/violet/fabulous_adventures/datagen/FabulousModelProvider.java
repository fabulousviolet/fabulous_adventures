package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;

import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;


public class FabulousModelProvider extends ModelProvider {

    public FabulousModelProvider(PackOutput output) {
        super(output, FabulousAdventures.MODID);
    }

    public static final TextureSlot BASE = TextureSlot.create("rope", TextureSlot.ALL);
    @SuppressWarnings("deprecation")
    public static final ModelTemplate ROPE_TEMPLATE = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model")),
            Optional.of(""),
            BASE);
    public static final ModelTemplate ROPE_TEMPLATE_END = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model_end")),
            Optional.of(""),
            BASE);

    public static final TexturedModel.Provider ROPE_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            block -> new TextureMapping()
                    .put(BASE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE
    );
    public static final TexturedModel.Provider ROPE_END_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            block -> new TextureMapping()
                    .put(BASE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE_END);

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        Identifier modelLoc_rope = ROPE_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE.get(), blockModels.modelOutput);
        Identifier modelLoc_rope_end = ROPE_END_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE_CLIMBABLE.get(), blockModels.modelOutput);
        Variant variant_rope = new Variant(modelLoc_rope);
        Variant variant_rope_end = new Variant(modelLoc_rope_end);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.ROPE.get(),
                                BlockModelGenerators.variant(variant_rope))
                        .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                .select(Direction.Axis.Z, BlockModelGenerators.X_ROT_90)
                                .select(Direction.Axis.X, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90)))
        );
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.ROPE_CLIMBABLE.get(),
                                BlockModelGenerators.variant(variant_rope))
                        .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                .select(Direction.Axis.Z,BlockModelGenerators.X_ROT_90)
                                .select(Direction.Axis.X,BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90)))
                        .with(PropertyDispatch.modify(RopeBlock.END)
                                .select(false, BlockModelGenerators.NOP)
                                .select(true, VariantMutator.MODEL.withValue(modelLoc_rope_end)))


        );


    }

}
