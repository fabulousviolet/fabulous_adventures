package com.violet.fabulous_adventures.item.custom.glider;

import com.violet.fabulous_adventures.FabulousAdventures;
import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    private static final int HURT_INTERVAL = 30;

    private static final Map<UUID, Float> glideSpeed = new HashMap<>();
    private static final Map<UUID, Vec3> heading = new HashMap<>();

    public static final float MIN_GLIDE_SPEED = 0.1f;
    private static final float MAX_GLIDE_SPEED = 0.8f;
    private static final float MAX_ACCEL_PER_TICK = 0.001f;
    private static final float MAX_DECEL_PER_TICK = 0.03f;
    private static final float MAX_TURN_AT_MIN_SPEED = 4f;
    private static final float MIN_TURN_AT_MAX_SPEED = 0.7f;
    private static final Map<UUID, Vec3> headingWindowStart = new HashMap<>();
    private static final int TURN_WINDOW_TICKS = 15;

    public static void setInitialSpeed(Player player, float speed) {
        glideSpeed.put(player.getUUID(), speed);
    }

    public static void setInitialHeading(Player player) {
        Vec3 look = player.getLookAngle().multiply(1, 0, 1);
        heading.put(player.getUUID(), look.lengthSqr() > 1.0E-5 ? look.normalize() : new Vec3(1, 0, 0));
    }

     // degrees/tick at top speed

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        UUID id = player.getUUID();

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
            Vec3 desiredDirection = player.getLookAngle().multiply(1, 0, 1);

            if (desiredDirection.lengthSqr() > 1.0E-5) {
                desiredDirection = desiredDirection.normalize();
                Vec3 currentHeading = heading.getOrDefault(id, desiredDirection);
                float speed = glideSpeed.getOrDefault(id, MIN_GLIDE_SPEED);

                // turn rate scales inversely with speed — easier to turn slow, harder to turn fast
                float speedFraction = Mth.clamp((speed - MIN_GLIDE_SPEED) / (MAX_GLIDE_SPEED - MIN_GLIDE_SPEED), 0f, 1f);
                float turnRate = Mth.lerp(speedFraction, MAX_TURN_AT_MIN_SPEED, MIN_TURN_AT_MAX_SPEED);

                Vec3 newHeading = rotateTowards(currentHeading, desiredDirection, turnRate);

                Vec3 windowStart = headingWindowStart.get(id);
                if (windowStart == null || player.tickCount % TURN_WINDOW_TICKS == 0) {
                    headingWindowStart.put(id, newHeading);
                    windowStart = newHeading;
                }

                double windowedDot = Mth.clamp(windowStart.dot(newHeading), -1.0, 1.0);
                double windowedAngle = Math.toDegrees(Math.acos(windowedDot));

                if (windowedAngle >= 90.0) {
                    speed = MIN_GLIDE_SPEED;
                } else if (windowedAngle <= 15.0) {
                    float factor = (float) (1.0 - windowedAngle / 15.0);
                    speed = Math.min(MAX_GLIDE_SPEED, speed + MAX_ACCEL_PER_TICK * factor);
                } else {
                    float factor = (float) ((windowedAngle - 15.0) / 75.0);
                    speed = Math.max(MIN_GLIDE_SPEED, speed - MAX_DECEL_PER_TICK * factor);
                }

                glideSpeed.put(id, speed);
                heading.put(id, newHeading);

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
            glideSpeed.remove(id);
            heading.remove(id);
        }


        if (player.level().isClientSide()) return;

        int currentSlot = player.getInventory().getSelectedSlot();
        Integer previousSlot = lastSelectedSlot.put(id, currentSlot);

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