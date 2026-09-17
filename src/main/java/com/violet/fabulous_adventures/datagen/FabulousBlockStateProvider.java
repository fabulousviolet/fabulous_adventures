package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block.custom.RopeBlock;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FabulousBlockStateProvider extends BlockStateProvider {

    public FabulousBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FabulousAdventures.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile ropeModel = models()
                .withExistingParent(
                        "rope", modLoc("block/rope_model")
                )
                .texture(
                        "rope",
                        blockTexture(FabulousBlocks.ROPE.get())
                );

        ModelFile ropeEndModel = models()
                .withExistingParent(
                        "rope_end",
                        modLoc("block/rope_model_end")
                )
                .texture(
                        "rope",
                        blockTexture(FabulousBlocks.ROPE.get())
                );

        getVariantBuilder(FabulousBlocks.ROPE.get())
                .forAllStates(state -> ropeModelForAxis(state, ropeModel));

        getVariantBuilder(FabulousBlocks.ROPE_CLIMBABLE.get())
                .forAllStates(state -> {

                    ModelFile model = state.getValue(RopeBlock.END)
                            ? ropeEndModel
                            : ropeModel;

                    int rotationX = 0;
                    int rotationY = 0;

                    Direction.Axis axis =
                            state.getValue(BlockStateProperties.AXIS);

                    switch (axis) {
                        case Y -> {
                            rotationX = 0;
                            rotationY = 0;
                        }

                        case Z -> {
                            rotationX = 90;
                            rotationY = 0;
                        }

                        case X -> {
                            rotationX = 90;
                            rotationY = 90;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .build();
                });
        ModelFile mapDisplayModel = models()
                .withExistingParent(
                        "map_display",
                        modLoc("block/map_display_model")
                )
                .texture(
                        "map_display",
                        blockTexture(FabulousBlocks.MAP_DISPLAY.get())
                );

        getVariantBuilder(FabulousBlocks.MAP_DISPLAY.get())
                .forAllStates(state -> {

                    Direction facing =
                            state.getValue(BlockStateProperties.FACING);

                    int rotationX = 0;
                    int rotationY = 0;

                    switch (facing) {

                        case UP -> {
                            rotationX = 0;
                            rotationY = 0;
                        }

                        case DOWN -> {
                            rotationX = 180;
                            rotationY = 0;
                        }

                        case NORTH -> {
                            rotationX = 90;
                            rotationY = 0;
                        }

                        case SOUTH -> {
                            rotationX = 90;
                            rotationY = 180;
                        }

                        case WEST -> {
                            rotationX = 90;
                            rotationY = 270;
                        }

                        case EAST -> {
                            rotationX = 90;
                            rotationY = 90;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(mapDisplayModel)
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .build();
                });
        ModelFile oxygenTankModel = models()
                .withExistingParent(
                        "oxygen_tank",
                        modLoc("block/oxygen_tank_model")
                )
                .texture(
                        "oxygen_tank",
                        blockTexture(FabulousBlocks.OXYGEN_TANK.get())
                );

        simpleBlock(
                FabulousBlocks.OXYGEN_TANK.get(),
                oxygenTankModel
        );
        simpleBlock(
                FabulousBlocks.ROPE_BUILDER.get(),
                models().cubeAll(
                        "rope_builder",
                        blockTexture(FabulousBlocks.ROPE_BUILDER.get())
                )
        );
    }

    private ConfiguredModel[] ropeModelForAxis(BlockState state, ModelFile model) {
        Direction.Axis axis =
                state.getValue(BlockStateProperties.AXIS);

        int rotationX = 0;
        int rotationY = 0;

        switch (axis) {

            case Y -> {
                rotationX = 0;
                rotationY = 0;
            }

            case Z -> {
                rotationX = 90;
                rotationY = 0;
            }

            case X -> {
                rotationX = 90;
                rotationY = 90;
            }
        }

        return ConfiguredModel.builder()
                .modelFile(model)
                .rotationX(rotationX)
                .rotationY(rotationY)
                .build();
    }
}
