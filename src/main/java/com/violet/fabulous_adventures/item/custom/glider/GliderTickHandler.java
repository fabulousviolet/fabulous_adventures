package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class GliderTickHandler {
    private static final Map<UUID, Integer> lastSelectedSlot = new HashMap<>();
    private static final double MAX_FALL_SPEED = -0.04;
    //executes on every tick
    //checks when the glider should be deactivated (when deselecting it or hitting the ground)
    //adjusts slowfalling speed when aplifier is 1
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        //deactivate when hitting ground
        Player player = event.getEntity();
        if (player.onGround()){
            player.removeEffect(MobEffects.SLOW_FALLING);
            player.getItemInHand(InteractionHand.MAIN_HAND).set(FabulousDataComponents.GLIDER_ACTIVE.get(),false);

        }
        //limit the downwards y velocity when having the slowfalling effect with amplifier 1
        MobEffectInstance slowFalling = player.getEffect(MobEffects.SLOW_FALLING);
        if (slowFalling != null && slowFalling.getAmplifier() == 1) {
            Vec3 motion = player.getDeltaMovement();
            if (motion.y < MAX_FALL_SPEED) {
                player.setDeltaMovement(motion.x, MAX_FALL_SPEED, motion.z);
            }
        }
        if (player.level().isClientSide()) return;
        //deactivate glider when deselecting it
        int currentSlot = player.getInventory().getSelectedSlot();
        Integer previousSlot = lastSelectedSlot.put(player.getUUID(), currentSlot);

        if (previousSlot != null && previousSlot != currentSlot) {
            ItemStack previousStack = player.getInventory().getItem(previousSlot);
            if (previousStack.getItem() instanceof GliderItem && player.hasEffect(MobEffects.SLOW_FALLING)) {
                player.removeEffect(MobEffects.SLOW_FALLING);
                player.getItemInHand(InteractionHand.MAIN_HAND).set(FabulousDataComponents.GLIDER_ACTIVE.get(),false);

            }
        }

    }
}