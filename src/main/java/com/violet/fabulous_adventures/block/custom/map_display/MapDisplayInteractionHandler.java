package com.violet.fabulous_adventures.block.custom.map_display;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.block.FabulousBlocks;
import com.violet.fabulous_adventures.block_entity.custom.MapDisplay.MapDisplay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class MapDisplayInteractionHandler {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        if (!player.isShiftKeyDown()) return;
        if (!player.getItemInHand(event.getHand()).isEmpty()) return;
        if (!level.getBlockState(pos).is(FabulousBlocks.MAP_DISPLAY.get())) return;
        if (level.isClientSide()) return;

        if (level.getBlockEntity(pos) instanceof MapDisplay display) {
            BlockPos anchorPos = display.isAnchor() ? pos : display.getAnchorPos();

            if (level.getBlockEntity(anchorPos) instanceof MapDisplay anchor) {
                Direction.Axis axis = level.getBlockState(pos).getValue(MapDisplayBlock.FACING).getAxis();
                anchor.makeAnchor(null, anchor.getLocalU(), anchor.getLocalV(), anchor.getSquareSize());

                for (BlockPos memberPos : MapDisplayBlock.getSquareMembers(new MapDisplaySquare(anchorPos.getX(), anchorPos.getY(), anchorPos.getZ(), anchor.getSquareSize(), axis))) {
                    if (!memberPos.equals(anchorPos) && level.getBlockEntity(memberPos) instanceof MapDisplay child) {
                        child.makeChild(anchorPos, null, child.getLocalU(), child.getLocalV(), child.getSquareSize());
                    }
                }

                event.setCanceled(true); // prevent vanilla's default sneak-place-against-block behavior from also firing
            }
        }
    }
}
