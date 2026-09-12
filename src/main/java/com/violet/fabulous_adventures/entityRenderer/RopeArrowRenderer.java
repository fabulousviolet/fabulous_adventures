package com.violet.fabulous_adventures.entityRenderer;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.entity.custom.RopeArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RopeArrowRenderer extends ArrowRenderer<RopeArrow, ArrowRenderState> {
    public RopeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    //define arrow texture location
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/entity/rope_arrow.png");
    //returns the texture location
    @Override
    protected @NonNull ResourceLocation getTextureLocation(@NonNull ArrowRenderState arrowRenderState) {
        return TEXTURE;
    }
    //creates a new renderState
    @Override
    public @NonNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }







}
