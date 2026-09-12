package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkilltreeStructure {
    public static final Map<String, String> NODE_PARENTS = load();
    public static final Map<String, List<String>> NODE_CHILDREN = invert(NODE_PARENTS);

    private static Map<String, String> load() {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "skilltree/skilltree_structure.json");
        try (InputStream stream = SkilltreeStructure.class.getResourceAsStream("/data/" + location.getNamespace() + "/" + location.getPath())) {
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return new Gson().fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load skilltree nodes", e);
        }
    }
    private static Map<String, List<String>> invert(Map<String, String> parents) {
        Map<String, List<String>> children = new HashMap<>();
        for (var entry : parents.entrySet()) {
            children.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }
        return children;
    }
}