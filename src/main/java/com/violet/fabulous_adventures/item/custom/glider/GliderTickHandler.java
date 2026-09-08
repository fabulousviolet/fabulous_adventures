package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.core.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class GliderTickHandler {
    private static final Map<Player, Integer> lastSelectedSlot = new HashMap<>();
    private static final int HURT_INTERVAL = 30;

    private static final Map<Player, Float> glideSpeed = new HashMap<>();
    private static final Map<Player, Vec3> heading = new HashMap<>();
    private static final Map<Player, Vec3> headingWindowStart = new HashMap<>();
    private static final Set<Player> justActivated = new HashSet<>();

    public static final float MIN_GLIDE_SPEED = 0.1f;
    private static final float MAX_GLIDE_SPEED = 1f;
    private static final float MAX_ACCEL_PER_TICK = 0.002f;
    private static final float MAX_DECEL_PER_TICK = 0.01f;
    private static final float MAX_TURN_AT_MIN_SPEED = 4f;
    private static final float MIN_TURN_AT_MAX_SPEED = 0.5f;
    private static final int TURN_WINDOW_TICKS = 20;
    private static final double DECEL_START = 15.0;
    private static final double HARD_STOP = 160.0;

    public static void markJustActivated(Player player) {
        justActivated.add(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        double maxFallSpeed = SkillUtils.isUnlocked(player, "glider_fall_speed") ? -0.02 : -0.04;

        boolean gliding = isGliderActiveInHand(player, InteractionHand.MAIN_HAND)
                || isGliderActiveInHand(player, InteractionHand.OFF_HAND);

        if (player.onGround()) {
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof GliderItem && stack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false)) {
                    stack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
                }
            }
        }

        if (gliding) {
            if (justActivated.remove(player)) {
                Vec3 currentMotion = player.getDeltaMovement().multiply(1, 0, 1);
                float initialSpeed = Math.max((float) currentMotion.length(), MIN_GLIDE_SPEED);
                glideSpeed.put(player, initialSpeed);
                heading.put(player, currentMotion.lengthSqr() > 1.0E-5
                        ? currentMotion.normalize()
                        : player.getLookAngle().multiply(1, 0, 1).normalize());
                headingWindowStart.remove(player);
            }

            Vec3 desiredDirection = player.getLookAngle().multiply(1, 0, 1);

            if (desiredDirection.lengthSqr() > 1.0E-5) {
                desiredDirection = desiredDirection.normalize();
                Vec3 currentHeading = heading.getOrDefault(player, desiredDirection);
                float speed = glideSpeed.getOrDefault(player, MIN_GLIDE_SPEED);

                float speedFraction = Mth.clamp((speed - MIN_GLIDE_SPEED) / (MAX_GLIDE_SPEED - MIN_GLIDE_SPEED), 0f, 1f);
                float turnRate = Mth.lerp(speedFraction, MAX_TURN_AT_MIN_SPEED, MIN_TURN_AT_MAX_SPEED);

                Vec3 newHeading = rotateTowards(currentHeading, desiredDirection, turnRate);

                double viewDot = Mth.clamp(currentHeading.dot(desiredDirection), -1.0, 1.0);
                double viewAngle = Math.toDegrees(Math.acos(viewDot));

                Vec3 windowStart = headingWindowStart.get(player);
                if (windowStart == null || player.tickCount % TURN_WINDOW_TICKS == 0) {
                    headingWindowStart.put(player, newHeading);
                    windowStart = newHeading;
                }
                double windowedDot = Mth.clamp(windowStart.dot(newHeading), -1.0, 1.0);
                double windowedAngle = Math.toDegrees(Math.acos(windowedDot));

                double effectiveAngle = viewAngle > 90.0 ? viewAngle : windowedAngle;

                if (effectiveAngle >= HARD_STOP) {
                    speed = MIN_GLIDE_SPEED;
                } else if (effectiveAngle <= DECEL_START) {
                    float factor = (float) (1.0 - effectiveAngle / DECEL_START);
                    speed = Math.min(MAX_GLIDE_SPEED, speed + MAX_ACCEL_PER_TICK * factor);
                } else {
                    float factor = (float) ((effectiveAngle - DECEL_START) / (HARD_STOP - DECEL_START));
                    speed = Math.max(MIN_GLIDE_SPEED, speed - MAX_DECEL_PER_TICK * factor);
                }

                glideSpeed.put(player, speed);
                heading.put(player, newHeading);

                Vec3 horizontal = newHeading.scale(speed);
                Vec3 fullMotion = player.getDeltaMovement();
                player.setDeltaMovement(horizontal.x, fullMotion.y, horizontal.z);
            }

            Vec3 motion = player.getDeltaMovement();
            if (motion.y < maxFallSpeed) {
                player.setDeltaMovement(motion.x, maxFallSpeed, motion.z);
            }
            player.resetFallDistance();
        } else {
            glideSpeed.remove(player);
            heading.remove(player);
            headingWindowStart.remove(player);
            justActivated.remove(player);
        }

        if (player.level().isClientSide()) return;

        int currentSlot = player.getInventory().getSelectedSlot();
        Integer previousSlot = lastSelectedSlot.put(player, currentSlot);

        if (previousSlot != null && previousSlot != currentSlot) {
            ItemStack previousStack = player.getInventory().getItem(previousSlot);
            if (previousStack.getItem() instanceof GliderItem && previousStack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false)) {
                previousStack.set(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
            }
        }

        if (player.level().getGameTime() % HURT_INTERVAL != 0) return;

        for (InteractionHand hand : InteractionHand.values()) {
            if (isGliderActiveInHand(player, hand)) {
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
            }
        }
    }

    private static Vec3 rotateTowards(Vec3 from, Vec3 to, float maxDegrees) {
        double dot = Mth.clamp(from.dot(to), -1.0, 1.0);
        double angle = Math.toDegrees(Math.acos(dot));
        if (angle <= maxDegrees || angle < 1.0E-4) return to;

        float t = (float) (maxDegrees / angle);
        Vec3 lerped = from.scale(1 - t).add(to.scale(t));
        return lerped.lengthSqr() > 1.0E-5 ? lerped.normalize() : to;
    }

    private static boolean isGliderActiveInHand(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return stack.getItem() instanceof GliderItem && stack.getOrDefault(FabulousDataComponents.GLIDER_ACTIVE.get(), false);
    }
}