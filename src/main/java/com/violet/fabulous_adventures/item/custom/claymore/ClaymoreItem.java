package com.violet.fabulous_adventures.item.custom.claymore;

import com.violet.fabulous_adventures.dataComponents.FabulousDataComponents;
import com.violet.fabulous_adventures.skills.SkillUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ClaymoreItem extends Item {
    public static float DAMAGE_FACTOR = 0.15f;
    public static float MAX_CHARGE_BONUS = 2.0f;
    public static double ATTACK_RADIUS = 2;
    public static int MAX_CHARGE_TICKS = 80;
    public static final double KNOCKBACK_STRENGTH = 1;
    private static final int RELEASE_HOLD_TICKS = 40;

    public ClaymoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
        if(!SkillUtils.isUnlocked(player,"claymore_unlock")) {
            player.displayClientMessage(Component.literal("Claymore is not unlocked yet. Unlock it in the Skill tree (K)"),true);
            return InteractionResult.PASS;
        }
        if(SkillUtils.isUnlocked(player,"claymore_charge_speed")) {
            MAX_CHARGE_TICKS = 60;
            DAMAGE_FACTOR = 0.2f;
        }else if(SkillUtils.isUnlocked(player,"claymore_damage") && SkillUtils.isUnlocked(player, "claymore_charge_speed")) {
            MAX_CHARGE_TICKS = 60;
            DAMAGE_FACTOR = 0.25f;
        }else if(SkillUtils.isUnlocked(player,"claymore_damage")) {
            DAMAGE_FACTOR = 0.1875f;
            MAX_CHARGE_TICKS = 80;
        }else{
            DAMAGE_FACTOR = 0.15f;
            MAX_CHARGE_TICKS = 80;
        }
        if(SkillUtils.isUnlocked(player,"claymore_area")) {
            ATTACK_RADIUS = 3;
        }else{
            ATTACK_RADIUS = 2;
        }
        if(SkillUtils.isUnlocked(player,"claymore_max_charge_bonus")) {
            MAX_CHARGE_BONUS = 4f;
        }else{
            MAX_CHARGE_BONUS = 2f;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            stack.set(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public @NonNull AABB getSweepHitBox(@NonNull ItemStack stack, @NonNull Player player, Entity target) {
        return target.getBoundingBox().inflate(2);
    }

    @Override
    public boolean useOnRelease(@NonNull ItemStack itemStack) {
        return true;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack itemStack, @NonNull LivingEntity user) {
        return MAX_CHARGE_TICKS;
    }

    @Override
    public void onUseTick(Level level, @NonNull LivingEntity livingEntity, @NonNull ItemStack itemStack, int ticksRemaining) {
        if (level.isClientSide()) return;

        ClaymoreChargeState current = itemStack.getOrDefault(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.NORMAL);
        if(livingEntity instanceof Player player) {
            if(!SkillUtils.isUnlocked(player,"claymore_unlock")) {
                player.displayClientMessage(Component.literal("Claymore is not unlocked yet. Unlock it in the Skill tree (K)"),true);
                return;
            }
        }

        if (current == ClaymoreChargeState.RELEASE) return;

        itemStack.set(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.CHARGED);

    }
    public static float calculateDamage(int useDuration, int ticksCharged){
        float damage = DAMAGE_FACTOR * ticksCharged;
        if (ticksCharged >= useDuration){
            return damage + MAX_CHARGE_BONUS;
        }
        else return damage;
    }

    @Override
    public boolean releaseUsing(@NonNull ItemStack itemStack, @NonNull Level level, @NonNull LivingEntity entity, int remainingTime) {
        if (level.isClientSide()) return true;

        int useDuration = getUseDuration(itemStack,entity);
        int ticksCharged = useDuration-remainingTime;

        double damage = calculateDamage(useDuration,ticksCharged);
        performAttack(level,entity,damage);

        itemStack.set(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.RELEASE);

        itemStack.set(FabulousDataComponents.CLAYMORE_RELEASE_TICKS.get(), RELEASE_HOLD_TICKS);

        return true;

    }
    @Override
    public @NonNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity) {
        if (!level.isClientSide()) {
            int useDuration = getUseDuration(stack, entity);

            double damage = calculateDamage(useDuration, useDuration); // fully charged
            performAttack(level, entity, damage);

            stack.set(FabulousDataComponents.CLAYMORE_CHARGE_STATE.get(), ClaymoreChargeState.RELEASE);
            stack.set(FabulousDataComponents.CLAYMORE_RELEASE_TICKS.get(), RELEASE_HOLD_TICKS);
        }
        return stack;
    }

    private void performAttack(Level level, LivingEntity entity, double damage) {
        if (level.isClientSide()) return;

        AABB searchArea = entity.getBoundingBox().inflate(ATTACK_RADIUS);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, searchArea,
                target -> target != entity && target.isAlive());

        for (LivingEntity target : targets) {
            double distance = entity.distanceTo(target);
            if (distance > ATTACK_RADIUS) continue;

            DamageSource damageSource = entity.damageSources().mobAttack(entity);
            target.hurtServer((ServerLevel) level, damageSource, (float) damage);

            Vec3 knockbackDir = target.position().subtract(entity.position()).normalize();
            target.setDeltaMovement(target.getDeltaMovement()
                    .add(knockbackDir.x * KNOCKBACK_STRENGTH, 0.4, knockbackDir.z * KNOCKBACK_STRENGTH));
            target.hurtMarked = true; // ensures the knockback velocity syncs to clients
        }
        entity.getMainHandItem().hurtAndBreak(3,entity,getEquipmentSlot(entity.getMainHandItem()));
        spawnGroundParticles((ServerLevel) level, entity.position());
    }

    private void spawnGroundParticles(ServerLevel level, Vec3 center) {
        double groundY = center.y;

        for (int i = 0; i < 40; i++) {
            double angle = level.getRandom().nextDouble() * Math.PI * 2;
            double dist = level.getRandom().nextDouble() * ATTACK_RADIUS;
            double x = center.x + Math.cos(angle) * dist;
            double z = center.z + Math.sin(angle) * dist;

            level.sendParticles(ParticleTypes.EXPLOSION, x, groundY, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

}
