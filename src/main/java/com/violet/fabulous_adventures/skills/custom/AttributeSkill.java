package com.violet.fabulous_adventures.skills.custom;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.skills.Skill;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class AttributeSkill extends Skill {
    public Holder<Attribute> attributeType;
    public double addValue;
    public String suffix;
    @Override
    public void tick(Player player, boolean unlocked) {

    }
    public AttributeSkill(Holder<Attribute> attributeHolder, double value, String suffix) {
        this.attributeType = attributeHolder;
        this.addValue = value;
        this.suffix = suffix;
    }
    public void addAttribute(ServerPlayer player) {
        AttributeModifier modifer = new AttributeModifier(
                Identifier.fromNamespaceAndPath(FabulousAdventures.MODID,"attribute_skill"+ this.suffix),
                this.addValue,
                AttributeModifier.Operation.ADD_VALUE
        );
        player.getAttribute(this.attributeType).addOrReplacePermanentModifier(modifer);
    }
}

