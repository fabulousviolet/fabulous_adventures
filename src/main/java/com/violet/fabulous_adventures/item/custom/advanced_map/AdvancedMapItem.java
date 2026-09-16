package com.violet.fabulous_adventures.item.custom.advanced_map;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.core.FabulousMapDecorationTypes;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.item.FabulousItems;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.HashMap;
import java.util.Map;

public class AdvancedMapItem extends MapItem {
    public int MAX_SCALE = 0;
    public AdvancedMapItem(Properties properties) {
        super(properties);
    }
    public static ItemStack create(ServerLevel level, int originX, int originZ, byte scale, boolean trackPosition, boolean unlimitedTracking) {
        ItemStack map = new ItemStack(FabulousItems.ADVANCED_MAP.get());
        MapItemSavedData newData = MapItemSavedData.createFresh((double)originX, (double)originZ, (byte)scale, trackPosition, unlimitedTracking, level.dimension());
        MapId id = level.getFreeMapId();
        level.setMapData(id, newData);
        map.set(DataComponents.MAP_ID, id);
        return map;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity owner, int x, boolean a) {
        super.inventoryTick(itemStack, level, owner, x, a);
        if (SkillUtils.isUnlocked((Player) owner,"advanced_map_scale")){
            MAX_SCALE = 1;
        }
        if (SkillUtils.isUnlocked((Player) owner,"advanced_map_scale_1")){
            MAX_SCALE = 2;
        }

        if (SkillUtils.isUnlocked((Player) owner,"advanced_map_scale_2")){
            MAX_SCALE = 3;
        }

        MapItemSavedData savedData = getSavedData(itemStack, level);
        if (savedData == null) return;

        if (!isInsideMap(savedData, owner)) {
            MapId id = itemStack.get(DataComponents.MAP_ID);
            MapItemSavedData newMapData = savedData;

            if (savedData.scale >= MAX_SCALE) {
                if(SkillUtils.isUnlocked((Player) owner,"advanced_map_shift")){
                    int newCenterX = (int) (savedData.centerX + getDeltaXOutsideOfBounds(savedData, owner));
                    int newCenterZ = (int) (savedData.centerZ + getDeltaZOutsideOfBounds(savedData, owner));
                    if(level instanceof ServerLevel serverLevel){
                        newMapData = createExactCentered(serverLevel, savedData, newCenterX, newCenterZ);

                    }
                }
            } else {
                if(SkillUtils.isUnlocked((Player) owner,"advanced_map_scale")){
                    newMapData = savedData.scaled();
                }
            }

            copyOverlappingPixels(savedData, newMapData);
            Map<String, BlockPos> waypoints = itemStack.getOrDefault(FabulousDataComponents.WAYPOINT_POSITIONS.get(), Map.of());
            for (var entry : waypoints.entrySet()) {
                BlockPos waypointPos = entry.getValue();
                newMapData.addDecoration(FabulousMapDecorationTypes.WAYPOINT.getDelegate(), level, entry.getKey(),
                        waypointPos.getX(), waypointPos.getZ(), 180.0, null);
            }
            level.setMapData(id, newMapData);
            itemStack.set(DataComponents.MAP_ID, id);
        }
    }

    public static boolean isInsideMap(MapItemSavedData savedData, Entity owner) {
        int centerX = savedData.centerX;
        int centerZ = savedData.centerZ;
        double ownerX = owner.getX();
        double ownerZ = owner.getZ();
        double deltaX = ownerX - centerX;
        double deltaZ = ownerZ - centerZ;
        double scale = 1 << savedData.scale;
        return (deltaX <= 63*scale && deltaZ <= 63*scale && deltaX >= -63*scale && deltaZ >= -63*scale);
    }

    private static final double SHIFT_BUFFER = 16.0;

    public static double getDeltaXOutsideOfBounds(MapItemSavedData savedData, Entity owner) {
        int centerX = savedData.centerX;
        double ownerX = owner.getX();
        double deltaX = ownerX - centerX;
        double scale = 1 << savedData.scale;

        if (deltaX > 63 * scale) return deltaX - 63 * scale + SHIFT_BUFFER;
        if (deltaX < -63 * scale) return deltaX + 63 * scale - SHIFT_BUFFER;
        return 0;
    }

    public static double getDeltaZOutsideOfBounds(MapItemSavedData savedData, Entity owner) {
        int centerZ = savedData.centerZ;
        double ownerZ = owner.getZ();
        double deltaZ = ownerZ - centerZ;
        double scale = 1 << savedData.scale;

        if (deltaZ > 63 * scale) return deltaZ - 63 * scale + SHIFT_BUFFER;
        if (deltaZ < -63 * scale) return deltaZ + 63 * scale - SHIFT_BUFFER;
        return 0;
    }

    private static void copyOverlappingPixels(MapItemSavedData oldData, MapItemSavedData newData) {
        int oldScale = 1 << oldData.scale;
        int newScale = 1 << newData.scale;

        for (int newPx = 0; newPx < 128; newPx++) {
            for (int newPz = 0; newPz < 128; newPz++) {
                int worldX = newData.centerX + (newPx - 64) * newScale;
                int worldZ = newData.centerZ + (newPz - 64) * newScale;

                int oldPx = Math.floorDiv(worldX - oldData.centerX, oldScale) + 64;
                int oldPz = Math.floorDiv(worldZ - oldData.centerZ, oldScale) + 64;

                if (oldPx >= 0 && oldPx < 128 && oldPz >= 0 && oldPz < 128) {
                    newData.colors[newPx + newPz * 128] = oldData.colors[oldPx + oldPz * 128];
                }
            }
        }
    }

    private static MapItemSavedData createExactCentered(ServerLevel level, MapItemSavedData template, int newCenterX, int newCenterZ) {
        CompoundTag tag =  new CompoundTag();

        template.save(tag, level.registryAccess());
        tag.putInt("xCenter", newCenterX);
        tag.putInt("zCenter", newCenterZ);
        tag.putByteArray("colors", new byte[16384]); // blank canvas — copyOverlappingPixels fills in what carries over

        return MapItemSavedData.load(tag,level.registryAccess());
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();

        if (level.getBlockState(clickedPos).is(FabulousBlocks.MAP_DISPLAY.get())) {
            return InteractionResult.PASS;
        }
        if (!SkillUtils.isUnlocked(context.getPlayer(), "advanced_map_waypoints")) return InteractionResult.PASS;
        ItemStack stack = context.getItemInHand();
        MapItemSavedData savedData = getSavedData(stack, level);
        if (savedData == null) return InteractionResult.PASS;

        String key = "waypoint-" + clickedPos.getX() + "-" + clickedPos.getZ();
        Map<String, BlockPos> waypoints = new HashMap<>(stack.getOrDefault(FabulousDataComponents.WAYPOINT_POSITIONS.get(), Map.of()));

        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            savedData.removeDecoration(key);
            waypoints.remove(key);
            stack.set(FabulousDataComponents.WAYPOINT_POSITIONS.get(), waypoints);
            return InteractionResult.SUCCESS;
        }

        savedData.addDecoration(FabulousMapDecorationTypes.WAYPOINT.getDelegate(), level, key,
                clickedPos.getX(), clickedPos.getZ(), 180.0, null);
        waypoints.put(key, clickedPos);
        stack.set(FabulousDataComponents.WAYPOINT_POSITIONS.get(), waypoints);
        return InteractionResult.SUCCESS;
    }
}
