package com.violet.fabulous_adventures.skills;

import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.skills.custom.AttributeSkill;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

@EventBusSubscriber(modid = FabulousAdventures.MODID)


public class SkillTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        Set<String> unlockedIds = player.getData(FabulousAttachments.UNLOCKED_SKILLS.get());
        for (DeferredHolder<Skill,? extends Skill> skill : FabulousSkills.SKILLS.getEntries()){
            if (skill.get() instanceof AttributeSkill attributeSkill){
                if(!player.getData(FabulousAttachments.UNLOCKED_SKILLS.get()).contains("attribute_skill"+attributeSkill.suffix)){
                    player.getAttribute(attributeSkill.attributeType).removeModifier(Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"attribute_skill"+ attributeSkill.suffix));
                }
            }
        }
        for (var entry : FabulousSkills.SKILL_REGISTRY.entrySet()) {
            String skillId = entry.getKey().identifier().getPath();
            boolean unlocked = unlockedIds.contains(skillId);
            entry.getValue().tick(player, unlocked);
        }
    }
}
