package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;
import java.util.function.BiConsumer;

public class FabulousModelProvider extends ModelProvider {

    public FabulousModelProvider(PackOutput output) {
        super(output, FabulousAdventures.MODID);
    }

    public static final TextureSlot BASE = TextureSlot.create("rope", TextureSlot.ALL);
    public static final ModelTemplate ROPE_TEMPLATE = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model")),
            Optional.of(""),
            BASE);
    public static final TexturedModel.Provider ROPE_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            block -> new TextureMapping()
                    .put(BASE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE
    );

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        Identifier modelLoc = ROPE_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE.get(), blockModels.modelOutput);
        Variant variant = new Variant(modelLoc);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.ROPE.get(),
                                BlockModelGenerators.variant(variant))
                        .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                .select(Direction.Axis.Z, BlockModelGenerators.X_ROT_90)
                                .select(Direction.Axis.X, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90)))
        );
    }

}
