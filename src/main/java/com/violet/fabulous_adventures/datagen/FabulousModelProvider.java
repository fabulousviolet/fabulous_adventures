package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.item.FabulousItems;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreChargeState;
import com.violet.fabulous_adventures.item.custom.claymore.ClaymoreState;
import com.violet.fabulous_adventures.item.custom.glider.GliderActive;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;

import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class FabulousModelProvider extends ModelProvider {

    public FabulousModelProvider(PackOutput output) {
        super(output, FabulousAdventures.MODID);
    }
//define texture slots here vvv
    public static final TextureSlot ROPE = TextureSlot.create("rope", TextureSlot.ALL);
    public static final TextureSlot MAP_DISPLAY = TextureSlot.create("map_display", TextureSlot.ALL);
    public static final TextureSlot OXYGEN_TANK = TextureSlot.create("oxygen_tank", TextureSlot.ALL);
//define model templates here vvv
    @SuppressWarnings("deprecation")
    public static final ModelTemplate ROPE_TEMPLATE = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model")),
            Optional.of(""),
        ROPE);
    public static final ModelTemplate ROPE_TEMPLATE_END = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:rope_model_end")),
            Optional.of(""),
            ROPE);
    public static final ModelTemplate MAP_DISPLAY_TEMPLATE = new ModelTemplate(
            Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:map_display_model")),
            Optional.of(""),
            MAP_DISPLAY);
    public static final ModelTemplate OXYGEN_TANK_TEMPLATE = new ModelTemplate(
                Optional.of(ModelLocationUtils.decorateBlockModelLocation("fabulousadventures:oxygen_tank_model")),
                Optional.of(""),
                OXYGEN_TANK);

//define model providers here vvv
    public static final TexturedModel.Provider ROPE_TEMPLATE_PROVIDER = TexturedModel.createDefault(
        _ -> new TextureMapping()
                    .put(ROPE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE
    );
    public static final TexturedModel.Provider ROPE_END_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            _ -> new TextureMapping()
                    .put(ROPE,TextureMapping.getBlockTexture(FabulousBlocks.ROPE.get(),"")),
            ROPE_TEMPLATE_END);
    public static final TexturedModel.Provider MAP_DISPLAY_TEMPLATE_PROVIDER = TexturedModel.createDefault(
            _ -> new TextureMapping()
                    .put(MAP_DISPLAY,TextureMapping.getBlockTexture(FabulousBlocks.MAP_DISPLAY.get(),"")),
            MAP_DISPLAY_TEMPLATE);
    public static final TexturedModel.Provider OXYGEN_TANK_TEMPLATE_PROVIDER = TexturedModel.createDefault(
                _ -> new TextureMapping()
                        .put(OXYGEN_TANK,TextureMapping.getBlockTexture(FabulousBlocks.OXYGEN_TANK.get(),"")),
                OXYGEN_TANK_TEMPLATE);

//register block/item models here vvv
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
    //define model locations here vvv
        ResourceLocation modelLoc_rope = ROPE_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE.get(), blockModels.modelOutput);
        ResourceLocation modelLoc_rope_end = ROPE_END_TEMPLATE_PROVIDER.create(FabulousBlocks.ROPE_CLIMBABLE.get(), blockModels.modelOutput);
        ResourceLocation modelLoc_map_display = MAP_DISPLAY_TEMPLATE_PROVIDER.create(FabulousBlocks.MAP_DISPLAY.get(), blockModels.modelOutput);
        Variant variant_rope = new Variant(modelLoc_rope);
        Variant variant_map_display = new Variant(modelLoc_map_display);

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
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(FabulousBlocks.MAP_DISPLAY.get(),
                                BlockModelGenerators.variant(variant_map_display))
                        .with(PropertyDispatch.modify(BlockStateProperties.FACING)
                                .select(Direction.UP, BlockModelGenerators.NOP)
                                .select(Direction.DOWN, BlockModelGenerators.X_ROT_180)
                                .select(Direction.NORTH,  BlockModelGenerators.X_ROT_90)
                                .select(Direction.SOUTH,  BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
                                .select(Direction.WEST,BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
                                .select(Direction.EAST,BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
                        )

        );
        blockModels.createTrivialBlock(FabulousBlocks.OXYGEN_TANK.get(),OXYGEN_TANK_TEMPLATE_PROVIDER);
        //rope builder model (textureless cube)
        blockModels.createTrivialCube(FabulousBlocks.ROPE_BUILDER.get());
    //define item models for items here vvv
        //rope arrow model
        itemModels.generateFlatItem(FabulousItems.ROPE_ARROW.get(), ModelTemplates.FLAT_ITEM);
        //glider model (bool 3d model(dependent on GLiderActive() Data Component))
        itemModels.itemModelOutput.accept(
                FabulousItems.GLIDER.get(),
                new ConditionalItemModel.Unbaked(
                        new GliderActive(),
                        new BlockModelWrapper.Unbaked(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"item/glider_active"),List.of()),
                        new BlockModelWrapper.Unbaked(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"item/glider"),List.of())

                )
        );
        //machete model
        itemModels.generateFlatItem(FabulousItems.MACHETE.get(),ModelTemplates.FLAT_ITEM);
        //claymore model
        List<SelectItemModel.SwitchCase<ClaymoreChargeState>> cases = List.of(
                new SelectItemModel.SwitchCase<>(
                        List.of(ClaymoreChargeState.NORMAL),
                        new BlockModelWrapper.Unbaked(
                                ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "item/claymore_normal"),
                                List.of())
                ),
                new SelectItemModel.SwitchCase<>(
                        List.of(ClaymoreChargeState.CHARGED),
                        new BlockModelWrapper.Unbaked(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "item/claymore_charged"),
                                List.of())
                ),
                new SelectItemModel.SwitchCase<>(
                        List.of(ClaymoreChargeState.RELEASE),
                        new BlockModelWrapper.Unbaked(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "item/claymore_release"),
                                List.of())
                )
        );

        itemModels.itemModelOutput.accept(
                FabulousItems.CLAYMORE.get(),
                new SelectItemModel.Unbaked(
                        new SelectItemModel.UnbakedSwitch<>(new ClaymoreState(), cases),
                        Optional.empty()
                )
        );
        itemModels.generateFlatItem(FabulousItems.EMPTY_ADVANCED_MAP.get(), ModelTemplates.FLAT_ITEM);
        ResourceLocation advancedMapModel = itemModels.generateLayeredItem(ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "item/advanced_map"), ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"item/advanced_map"),ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,"item/advanced_map_markings"));
        itemModels.itemModelOutput.accept(
                FabulousItems.ADVANCED_MAP.get(),
                new BlockModelWrapper.Unbaked(advancedMapModel, List.of())
        );
    }

}
