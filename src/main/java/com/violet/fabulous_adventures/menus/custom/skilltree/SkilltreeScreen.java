package com.violet.fabulous_adventures.menus.custom.skilltree;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.core.FabulousKeybinds;
import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.item.FabulousItems;
import com.violet.fabulous_adventures.menus.custom.skillpoint_progress.OpenSkillpointProgressPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SkilltreeScreen extends AbstractContainerScreen<SkilltreeMenu> {

    private static final ResourceLocation BACKGROUND_INNER = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/gui/skilltree_inner_bg.png");
    private static final ResourceLocation BACKGROUND_FRAME = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "textures/gui/skilltree_bg.png");
    private double scrollX = 0;
    private double scrollY = 0;
    private ImageButton skillpoint_progress_button;

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        scrollX += dragX;
        scrollY += dragY;
        return true;
    }

    public static final WidgetSprites NODE_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "node"),
            ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "node_disabled"),
            ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "node_highlighted")
    );
    public static final WidgetSprites SKILLPOINT_PROGRESS_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath("minecraft", "recipe_book/button"),
            ResourceLocation.fromNamespaceAndPath("minecraft", "recipe_book/button_highlighted")
    );

    public SkilltreeScreen(SkilltreeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 252;
        this.imageHeight = 166;
    }

    private static final Map<String, SkilltreeLayoutComputing.NodePosition> LAYOUT = SkilltreeLayoutComputing.computeLayout();

    public static SkillnodeDef createSkillNodeDef(String id, int cost, ResourceLocation display_icon, Component tooltip) {
        SkilltreeLayoutComputing.NodePosition pos = LAYOUT.get(id);
        return new SkillnodeDef(id, SkilltreeStructure.NODE_PARENTS.get(id), cost, (int) pos.x(), pos.y(), new SkillIcon.Texture(display_icon),tooltip);
    }
    public static SkillnodeDef createSkillNodeDef(String id, int cost, ItemStack display_item,Component tooltip){
        SkilltreeLayoutComputing.NodePosition pos = LAYOUT.get(id);
        return new SkillnodeDef(id, SkilltreeStructure.NODE_PARENTS.get(id), cost, (int) pos.x(), pos.y(), new SkillIcon.Item(display_item),tooltip);
    }

    public static final Set<SkillnodeDef> NODES = Set.of(
            createSkillNodeDef("wall_stick_skill", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "wall_stick_icon"), Component.literal("Wall Stick Skill").withColor(5636095)
                    .append(Component.literal("\nLets you stick to the side of blocks when holding down the shift key.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("crawl_skill", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "crawl_skill_icon"), Component.literal("Crawl Skill").withColor(5636095)
                    .append(Component.literal("\nLets you get into the crawling position whenever using the ")
                            .withColor(16777215))
                    .append(Component.keybind(FabulousKeybinds.CRAWL_SKILL_KEYBIND.getName()).withColor(16777045))

                    .append(Component.literal(" key.").withColor(16777215)
                            .append(Component.literal("\nCost: 1"))
                    )),
            createSkillNodeDef("repair_skill", 3, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "anvil"), Component.literal("Magic Repair").withColor(5636095)
                    .append(Component.literal("\nLets you repair Items consuming experience when shift clicking an anvil.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 3")
                    )),
            createSkillNodeDef("glider_unlock", 2, new ItemStack(FabulousItems.GLIDER.get()), Component.literal("Unlock glider").withColor(5636095)
                    .append(Component.literal("\nUse the glider off the ground to glide through the skies.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("machete_unlock", 1, new ItemStack(FabulousItems.MACHETE.get()), Component.literal("Unlock machete").withColor(5636095)
                    .append(Component.literal("\nMine or right-click blocks with the machete to clear an area of vegetation")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("rope_arrow_unlock", 2, new ItemStack(FabulousItems.ROPE_ARROW.get()), Component.literal("Unlock rope-arrows").withColor(5636095)
                    .append(Component.literal("\nShoot a rope-arrow at a ceiling to let down a rope you can climb on letting you reach high places easier.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("claymore_unlock", 3, new ItemStack(FabulousItems.CLAYMORE.get()), Component.literal("Unlock Claymore").withColor(5636095)
                    .append(Component.literal("\nHold down right click with the claymore in hand to charge up a powerful area attack. Let go to unleash it. The longer you charge, the mightier the attack. Gives a damage bonus when charged to the max.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 3")
                    )),
            createSkillNodeDef("advanced_map_unlock", 2, new ItemStack(FabulousItems.ADVANCED_MAP.get()), Component.literal("Unlock Advanced Map").withColor(5636095)
                    .append(Component.literal("\nA more sophisticated and upgradeable version of the map.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),

            createSkillNodeDef("claymore_charge_speed", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "time_speed_up"), Component.literal("Faster claymore charge").withColor(5636095)
                    .append(Component.literal("\nLets you charge up your claymore even faster accumulating damage quicker.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("claymore_damage", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "attack_up"), Component.literal("Increase claymore damage").withColor(5636095)
                    .append(Component.literal("\nLets you deal even more damage with your claymore's area attack")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("claymore_area", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "area_up"), Component.literal("Increase claymore attack area").withColor(5636095)
                    .append(Component.literal("\nHit enemies in an even higher radius with the area attack")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("claymore_max_charge_bonus", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "attack_up"), Component.literal("Increase claymore max charge damage bonus").withColor(5636095)
                    .append(Component.literal("\nGain even more damage when fully charging the claymore's attack.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("machete_area", 3, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "area_up"), Component.literal("Increase machete area").withColor(5636095)
                    .append(Component.literal("\nRemove vegetation in an even bigger radius")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 3")
                    )),
            createSkillNodeDef("glider_fall_speed", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "glider_down"), Component.literal("Slower glider fall").withColor(5636095)
                    .append(Component.literal("\nWhen gliding you lose less altitude letting you cover greater distances.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),

            createSkillNodeDef("advanced_map_scale", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "area_up"), Component.literal("Advanced Map Scaling").withColor(5636095)
                    .append(Component.literal("\nLets the map scale automatically once you leave its bounds.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("advanced_map_scale_1", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "area_up"), Component.literal("Advanced Map bigger scaling").withColor(5636095)
                    .append(Component.literal("\nLets the map scale bigger.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("advanced_map_scale_2", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "area_up"), Component.literal("Advanced Map even bigger scaling").withColor(5636095)
                    .append(Component.literal("\nLets the map scale even bigger.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("advanced_map_shift", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "shift"), Component.literal("Advanced Map Shifting").withColor(5636095)
                    .append(Component.literal("\nLets the Map shift with you when you're out of bounds and reached its max scale.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("advanced_map_waypoints", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "waypoint"), Component.literal("Advanced Map Waypoints").withColor(5636095)
                    .append(Component.literal("\nRight-click a block with the map to add a waypoint there. Shift + Right-click to remove it.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("advanced_map_waypoints_plus", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "waypoint"), Component.literal("Advanced Map Waypoints+").withColor(5636095)
                    .append(Component.literal("\nWaypoints are displayed in your world when holding the map.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),

            createSkillNodeDef("attribute_skill_speed", 1, new ItemStack(Items.FEATHER.asItem()), Component.literal("Increase movement speed").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your movement speed")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_max_health", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "health_up"), Component.literal("Increase max health").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your maximum health")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("attribute_skill_oxygen", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "oxygen_up"), Component.literal("Increase oxygen").withColor(5636095)
                    .append(Component.literal("\nLets you breathe longer under water")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_strength", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "attack_up"), Component.literal("Increase strength").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your base damage")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_attack_speed", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "time_speed_up"), Component.literal("Increase attack speed").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your attack speed")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_block_break_speed", 2, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "mine_up"), Component.literal("Increase mining speed").withColor(5636095)
                    .append(Component.literal("\nMine blocks quicker.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("attribute_skill_block_interaction_range", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "mine_range"), Component.literal("Increase block interaction range").withColor(5636095)
                    .append(Component.literal("\nLets you mine and interact with blocks from farther away.")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_entity_interaction_range", 1, ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "entity_range"), Component.literal("Increase mob interaction range").withColor(5636095)
                    .append(Component.literal("\nLets you hit and interact with entities from farther away")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    )),
            createSkillNodeDef("attribute_skill_sneak_speed", 2, new ItemStack(Items.IRON_LEGGINGS.asItem()), Component.literal("Increase sneak speed").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your sneak speed")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("attribute_skill_swim_speed", 2, new ItemStack(Items.FEATHER.asItem()), Component.literal("Increase swim speed").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your swim speed")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 2")
                    )),
            createSkillNodeDef("attribute_skill_knockback_resistance", 1, new ItemStack(Items.NETHERITE_CHESTPLATE.asItem()), Component.literal("Increase knockback resistance").withColor(5636095)
                    .append(Component.literal("\nPermanently buff your knockback resistance")
                            .withColor(16777215))
                    .append(Component.literal("\nCost: 1")
                    ))

    );

    private final Map<String, ImageButton> nodeButtons = new HashMap<>();

    @Override
    protected void init() {
        super.init();
        for (SkillnodeDef def : NODES) {
            ImageButton button = new ImageButton(leftPos + def.x(), topPos + def.y(), 20, 20, NODE_SPRITES, b -> {
                PacketDistributor.sendToServer(new SkilltreeButtonPayload(def.id(), def.cost(), def.parentId()));
            });
            button.setTooltip(Tooltip.create(def.tooltip()));
            nodeButtons.put(def.id(), button);
            this.addRenderableWidget(button);
        }
        skillpoint_progress_button = new ImageButton(imageWidth + leftPos + 5, topPos, 20 ,18, SKILLPOINT_PROGRESS_SPRITES,button ->{
            PacketDistributor.sendToServer(new OpenSkillpointProgressPayload());
        });
        this.addWidget(skillpoint_progress_button);
        updateButtonState();

    }

    private void updateButtonState() {
        Player player = Minecraft.getInstance().player;
        int points = player.getData(FabulousAttachments.SKILL_POINTS.get());
        Set<String> unlocked = player.getData(FabulousAttachments.UNLOCKED_SKILLS.get());

        for (SkillnodeDef def : NODES){
            ImageButton button = nodeButtons.get(def.id());
            boolean enoughPoints = points >= def.cost();
            boolean parentUnlocked = def.parentId() == null || unlocked.contains(def.parentId());
            boolean alreadyUnlocked = unlocked.contains(def.id());
            button.active = enoughPoints && parentUnlocked && !alreadyUnlocked;
        }
    }
    private void updateNodePositions(){
        for (SkillnodeDef def : NODES){
            int x = leftPos + def.x() + (int) scrollX;
            int y = topPos + def.y() + (int) scrollY;
            nodeButtons.get(def.id()).setPosition(x,y);
        }

    }
    private static final int CONTENT_INSET = 9;
    private static final int TILE_SIZE = 256;

    @Override
    public void renderBg(GuiGraphics graphics, float a, int mouseX, int mouseY) {
        int contentX0 = leftPos + CONTENT_INSET;
        int contentY0 = topPos + CONTENT_INSET+1;
        int contentX1 = leftPos + imageWidth - CONTENT_INSET;
        int contentY1 = topPos + imageHeight - CONTENT_INSET-24;

        graphics.enableScissor(contentX0, contentY0, contentX1, contentY1);

        int contentWidth = contentX1 - contentX0;
        int contentHeight = contentY1 - contentY0;


        int wrapX = Math.floorMod((int) scrollX, TILE_SIZE) - TILE_SIZE;
        int wrapY = Math.floorMod((int) scrollY, TILE_SIZE) - TILE_SIZE;

        for (int x = wrapX; x < contentWidth; x += TILE_SIZE) {
            for (int y = wrapY; y < contentHeight; y += TILE_SIZE) {
                graphics.blit(RenderType.GUI_TEXTURED, BACKGROUND_INNER,
                        contentX0 + x, contentY0 + y,
                        0, 0, TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        graphics.disableScissor();

        graphics.blit(RenderType.GUI_TEXTURED, BACKGROUND_FRAME, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        int points = Minecraft.getInstance().player.getData(FabulousAttachments.SKILL_POINTS.get());
        int labelOffset;
        if (points < 10) labelOffset = 119;
        else if (points >=10 && points < 100) labelOffset = 125;
        else labelOffset = 131;
        graphics.drawString(this.font, Component.literal("Skill Tree"), leftPos + 9, topPos + 6, -12566464, false);
        graphics.drawString(this.font, Component.literal("Available Skillpoints: "+ points), leftPos + imageWidth - labelOffset, topPos + 6, -12566464, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        this.renderBackground(graphics, mouseX, mouseY, a);

        int contentX0 = leftPos + CONTENT_INSET;
        int contentY0 = topPos + CONTENT_INSET + 8;
        int contentX1 = leftPos + imageWidth - CONTENT_INSET;
        int contentY1 = topPos + imageHeight - CONTENT_INSET - 26;

        graphics.enableScissor(contentX0, contentY0, contentX1, contentY1);

        for (SkillnodeDef def : NODES) {
            List<String> children = SkilltreeStructure.NODE_CHILDREN.get(def.id());
            if (children == null) continue;
            ImageButton parentButton = nodeButtons.get(def.id());
            for (String childId : children) {
                ImageButton childButton = nodeButtons.get(childId);
                if (childButton == null) continue;
                drawConnector(graphics, parentButton.getX(), parentButton.getY(), childButton.getX(), childButton.getY());
            }
        }

        for (SkillnodeDef def : NODES) {
            ImageButton button = nodeButtons.get(def.id());
            button.render(graphics, mouseX, mouseY, a);
            def.icon().render(graphics, button.getX() + 2, button.getY() + 2);
        }

        graphics.disableScissor();

        skillpoint_progress_button.render(graphics, mouseX, mouseY, a);
    }
    @Override
    protected void containerTick() {
        super.containerTick();
        updateButtonState();
        updateNodePositions();


    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int xm, int ym) {
    }
    private static final ResourceLocation CONNECTOR_H = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "connector_h");
    private static final ResourceLocation CONNECTOR_V = ResourceLocation.fromNamespaceAndPath(FabulousAdventures.MODID, "connector_v");
    private static final int CONNECTOR_THICKNESS = 4;

    private void drawConnector(GuiGraphics graphics, int parentX, int parentY, int childX, int childY) {
        int parentCenterX = parentX + 10;
        int parentCenterY = parentY + 10;
        int childCenterX = childX + 10;
        int childCenterY = childY + 10;

        int hLeft = Math.min(parentCenterX, childCenterX);
        int hRight = Math.max(parentCenterX, childCenterX);
        int hWidth = hRight - hLeft;

        if (hWidth > 0) {
            graphics.blitSprite(RenderType.GUI_TEXTURED, CONNECTOR_H,
                    hLeft, parentCenterY - CONNECTOR_THICKNESS / 2,
                    hWidth, CONNECTOR_THICKNESS);
        }

        int vTop = Math.min(parentCenterY, childCenterY);
        int vBottom = Math.max(parentCenterY, childCenterY);
        int vHeight = vBottom - vTop;

        if (vHeight > 0) {
            graphics.blitSprite(RenderType.GUI_TEXTURED, CONNECTOR_V,
                    childCenterX - CONNECTOR_THICKNESS / 2, vTop,
                    CONNECTOR_THICKNESS, vHeight);
        }
    }
}


