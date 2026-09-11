package com.violet.fabulous_adventures.menus.custom.skillpoint_progress;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SkillpointProgressEntries {
    public static final List<SkillpointProgressEntry> ENTRIES = load();

    private static List<SkillpointProgressEntry> load() {
        Identifier location = Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "skilltree/skillpoint_progress_entries.json");
        try (InputStream stream = SkillpointProgressEntries.class.getResourceAsStream(
                "/data/" + location.getNamespace() + "/" + location.getPath())) {
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            JsonArray array = new Gson().fromJson(reader, JsonArray.class);

            List<SkillpointProgressEntry> result = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                String id = obj.get("id").getAsString();
                Identifier icon = Identifier.parse(obj.get("icon").getAsString());
                Component label = Component.literal(obj.get("label").getAsString());
                SkillCriterionType type = SkillCriterionType.valueOf(obj.get("type").getAsString());
                result.add(new SkillpointProgressEntry(type, id, icon, label, i));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load skillpoint progress entries", e);
        }
    }
}
