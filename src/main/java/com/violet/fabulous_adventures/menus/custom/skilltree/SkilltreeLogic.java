package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.attachments.AttachmentDataSync;
import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.skills.FabulousSkills;
import com.violet.fabulous_adventures.skills.Skill;
import com.violet.fabulous_adventures.skills.custom.AttributeSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public class SkilltreeLogic {

    public SkilltreeLogic(ServerPlayer player, String nodeId, int cost,String parent){
        this.handleNode(player,nodeId, cost);


    }

    private void handleNode(ServerPlayer player, String nodeId, int cost) {
        int points = player.getData(FabulousAttachments.SKILL_POINTS.get());
        Set<String> unlocked = player.getData(FabulousAttachments.UNLOCKED_SKILLS.get());

        if (points < cost){
        }else{
            if (!unlocked.contains(nodeId)) {
                unlocked.add(nodeId);
                AttachmentDataSync.setUnlockedSkills(player, unlocked);
                AttachmentDataSync.setSkillPoints(player,points-cost);

                ResourceLocation nodeResourceLocation = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID,nodeId);
                //apply AttributeMod on unlock
                Skill skill = FabulousSkills.SKILL_REGISTRY.get(nodeResourceLocation);
                if (skill instanceof AttributeSkill attributeSkill){
                    attributeSkill.addAttribute(player);
                }
            }
            else{
            }

        }
    }
}
