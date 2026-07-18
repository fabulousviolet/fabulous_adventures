package com.violet.fabulous_adventures.entityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.entity.custom.RopeArrow;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class RopeArrowRenderer extends ArrowRenderer<RopeArrow, ArrowRenderState> {
    public RopeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    //define arrow texture location
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/entity/rope_arrow.png");
    //returns the texture location
    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return TEXTURE;
    }
    //creates a new renderState
    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }







}
