package com.violet.fabulous_adventures.skills;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.skills.custom.AttributeSkill;
import com.violet.fabulous_adventures.skills.custom.CrawlSkill;
import com.violet.fabulous_adventures.skills.custom.UnlockSkill;
import com.violet.fabulous_adventures.skills.custom.WallStickSkill;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class FabulousSkills {
    public static final ResourceKey<Registry<Skill>> SKILL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID, "skills"));

    public static final Registry<Skill> SKILL_REGISTRY = new RegistryBuilder<>(SKILL_REGISTRY_KEY)
            .sync(true)
            .create();

    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(SKILL_REGISTRY_KEY, FabulousAdventures.MODID);

    public static final DeferredHolder<Skill, Skill> WALL_STICK_SKILL = SKILLS.register("wall_stick_skill",WallStickSkill::new);
    public static final DeferredHolder<Skill, Skill> CRAWL_SKILL = SKILLS.register("crawl_skill", CrawlSkill::new);
    public static final DeferredHolder<Skill, Skill> REPAIR_SKILL = SKILLS.register("repair_skill", UnlockSkill::new);

    public static final DeferredHolder<Skill, Skill> GLIDER_UNLOCK = SKILLS.register("glider_unlock", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> MACHETE_UNLOCK = SKILLS.register("machete_unlock", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ROPE_ARROW_UNLOCK = SKILLS.register("rope_arrow_unlock", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> CLAYMORE_UNLOCK = SKILLS.register("claymore_unlock", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_UNLOCK = SKILLS.register("advanced_map_unlock", UnlockSkill::new);

    public static final DeferredHolder<Skill, Skill> CLAYMORE_CHARGE_SPEED = SKILLS.register("claymore_charge_speed", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> CLAYMORE_DAMAGE = SKILLS.register("claymore_damage", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> CLAYMORE_AREA = SKILLS.register("claymore_area", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> CLAYMORE_MAX_CHARGE_BONUS = SKILLS.register("claymore_max_charge_bonus", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> MACHETE_AREA = SKILLS.register("machete_area", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> GLIDER_FALL_SPEED = SKILLS.register("glider_fall_speed", UnlockSkill::new);

    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_MAX_SCALE_1 = SKILLS.register("advanced_map_scale_1", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_MAX_SCALE_2 = SKILLS.register("advanced_map_scale_2", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_SHIFTING = SKILLS.register("advanced_map_shift", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_SCALE = SKILLS.register("advanced_map_scale", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_WAYPOINTS = SKILLS.register("advanced_map_waypoints", UnlockSkill::new);
    public static final DeferredHolder<Skill, Skill> ADVANCED_MAP_WAYPOINTS_PLUS = SKILLS.register("advanced_map_waypoints_plus", UnlockSkill::new);

    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_SPEED = SKILLS.register("attribute_skill_speed",() ->  new AttributeSkill(Attributes.MOVEMENT_SPEED, 0.04,"_speed"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_MAX_HEALTH = SKILLS.register("attribute_skill_max_health",() ->  new AttributeSkill(Attributes.MAX_HEALTH, 4,"_max_health"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_OXYGEN = SKILLS.register("attribute_skill_oxygen",() ->  new AttributeSkill(Attributes.OXYGEN_BONUS,2 ,"_oxygen"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_STRENGTH = SKILLS.register("attribute_skill_strength",() ->  new AttributeSkill(Attributes.ATTACK_DAMAGE, 0.5,"_strength"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_ATTACK_SPEED = SKILLS.register("attribute_skill_attack_speed",() ->  new AttributeSkill(Attributes.ATTACK_SPEED, 1.5,"_attack_speed"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_BLOCK_BREAK_SPEED = SKILLS.register("attribute_skill_block_break_speed",() ->  new AttributeSkill(Attributes.BLOCK_BREAK_SPEED, 0.3,"_block_break_speed"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_BLOCK_INTERACTION_RANGE = SKILLS.register("attribute_skill_block_interaction_range",() ->  new AttributeSkill(Attributes.BLOCK_INTERACTION_RANGE, 1,"_block_interaction_range"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_ENTITY_INTERACTION_RANGE = SKILLS.register("attribute_skill_entity_interaction_range",() ->  new AttributeSkill(Attributes.ENTITY_INTERACTION_RANGE, 1,"_entity_interaction_range"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_SNEAK_SPEED = SKILLS.register("attribute_skill_sneak_speed",() ->  new AttributeSkill(Attributes.SNEAKING_SPEED, 0.2,"_sneak_speed"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_SKILL_SWIM_SPEED = SKILLS.register("attribute_skill_swim_speed",() ->  new AttributeSkill(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5,"_swim_speed"));
    public static final DeferredHolder<Skill, Skill> ATTRIBUTE_KNOCKBACK_RESISTANCE = SKILLS.register("attribute_skill_knockback_resistance",() ->  new AttributeSkill(Attributes.KNOCKBACK_RESISTANCE, 0.15,"_knockback_resistance"));

    public static void register(IEventBus eventBus) {
        SKILLS.register(eventBus);
    }

    @SubscribeEvent
    public static void addRegistry(NewRegistryEvent event){
        event.register(SKILL_REGISTRY);
    }
}

