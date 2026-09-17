package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.item.FabulousItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class FabulousItemModelProvider extends ItemModelProvider {

    public FabulousItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output,FabulousAdventures.MODID,existingFileHelper);
    }
    private static final ResourceLocation GLIDER_ACTIVE =
            ResourceLocation.fromNamespaceAndPath(
                    FabulousAdventures.MODID,
                    "glider_active"
            );

    private static final ResourceLocation CLAYMORE_STATE =
            ResourceLocation.fromNamespaceAndPath(
                    FabulousAdventures.MODID,
                    "claymore_state"
            );

//register item models here vvv

    @Override
    protected void registerModels() {
        basicItem(FabulousItems.ROPE_ARROW.get());

        basicItem(FabulousItems.MACHETE.get());

        ModelFile gliderNormal =
                getExistingFile(
                        modLoc("item/glider_model")
                );

        ModelFile gliderActive =
                getExistingFile(
                        modLoc("item/glider_active")
                );

        getBuilder("glider")
                .parent(gliderNormal)
                .override()
                .predicate(GLIDER_ACTIVE, 1.0F)
                .model(gliderActive);

        ModelFile claymoreNormal =
                getExistingFile(
                        modLoc("item/claymore_normal")
                );

        ModelFile claymoreCharged =
                getExistingFile(
                        modLoc("item/claymore_charged")
                );

        ModelFile claymoreRelease =
                getExistingFile(
                        modLoc("item/claymore_release")
                );

        getBuilder("claymore")
                .parent(claymoreNormal)

                .override()
                .predicate(CLAYMORE_STATE, 1.0F)
                .model(claymoreCharged)
                .end()

                .override()
                .predicate(CLAYMORE_STATE, 2.0F)
                .model(claymoreRelease);

        basicItem(FabulousItems.EMPTY_ADVANCED_MAP.get());

        ModelFile advancedMap =

        getBuilder("advanced_map")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture(
                        "layer0",
                        modLoc("item/advanced_map")
                )
                .texture(
                        "layer1",
                        modLoc("item/advanced_map_markings")
                );

        withExistingParent(
                "map_display",
                ResourceLocation.fromNamespaceAndPath(
                        FabulousAdventures.MODID,
                        "block/map_display_model"
                )
        );

        withExistingParent(
                "rope",
                ResourceLocation.fromNamespaceAndPath(
                        FabulousAdventures.MODID,
                        "block/rope_model"
                )
        );

        withExistingParent(
                "oxygen_tank",
                ResourceLocation.fromNamespaceAndPath(
                        FabulousAdventures.MODID,
                        "block/oxygen_tank_model"
                )
        );
    }
}
