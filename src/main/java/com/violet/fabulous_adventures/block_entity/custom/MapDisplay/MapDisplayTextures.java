package com.violet.fabulous_adventures.block_entity.custom.MapDisplay;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class MapDisplayTextures {

    private static final Map<MapId, MapTexture> TEXTURES = new HashMap<>();

    private MapDisplayTextures() {
    }

    public static ResourceLocation getTexture(MapId mapId, MapItemSavedData mapData) {
        MapTexture texture = TEXTURES.get(mapId);

        if (texture == null) {
            texture = new MapTexture(mapId);
            TEXTURES.put(mapId, texture);
        }

        texture.update(mapData);

        return texture.location;
    }

    public static void clear() {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();

        for (MapTexture texture : TEXTURES.values()) {
            textureManager.release(texture.location);
            texture.texture.close();
        }

        TEXTURES.clear();
    }

    private static class MapTexture {

        private final ResourceLocation location;
        private final DynamicTexture texture;

        private byte[] previousColors;

        private MapTexture(MapId mapId) {
            Minecraft minecraft = Minecraft.getInstance();

            this.location = ResourceLocation.fromNamespaceAndPath(
                    "fabulous_adventures",
                    "map_display/map_" + mapId.id()
            );

            NativeImage image = new NativeImage(128, 128, false);
            this.texture = new DynamicTexture(image);

            minecraft.getTextureManager().register(
                    this.location,
                    this.texture
            );
        }

        private void update(MapItemSavedData mapData) {
            byte[] colors = mapData.colors;

            // Don't upload the texture if the map hasn't changed.
            if (previousColors != null && Arrays.equals(previousColors, colors)) {
                return;
            }

            NativeImage image = texture.getPixels();

            if (image == null) {
                return;
            }

            for (int y = 0; y < 128; y++) {
                for (int x = 0; x < 128; x++) {
                    int packedColor = colors[y * 128 + x] & 0xFF;

                    int color = getMapColor(packedColor);

                    image.setPixelRGBA(x, y, color);
                }
            }

            texture.upload();

            previousColors = colors.clone();
        }

        private static int getMapColor(int colorId) {
            return MapColor.getColorFromPackedId(colorId & 0xFF);
        }
    }
}