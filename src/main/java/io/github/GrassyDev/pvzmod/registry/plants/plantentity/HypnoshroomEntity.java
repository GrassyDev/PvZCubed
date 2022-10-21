package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.Optional;

public class HypnoshroomEntity extends GolemEntity implements IAnimatable, RangedAttackMob {
    public AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Byte> SNOW_GOLEM_FLAGS;
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private String controllerName = "hypnocontroller";
    public boolean isAsleep;
    public boolean isTired;
    public int healingTime;
    private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
    private LivingEntity cachedBeamTarget;
    private int beamTicks;

    public HypnoshroomEntity(EntityType<? extends HypnoshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isTired) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("hypnoshroom.asleep", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("hypnoshroom.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.contains("APX")) {
            int i = tag.getInt("APX");
            int j = tag.getInt("APY");
            int k = tag.getInt("APZ");
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(new BlockPos(i, j, k)));
        } else {
            this.dataTracker.set(ATTACHED_BLOCK, Optional.empty());
        }
    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        BlockPos blockPos = this.getAttachedBlock();
        if (blockPos != null) {
            tag.putInt("APX", blockPos.getX());
            tag.putInt("APY", blockPos.getY());
            tag.putInt("APZ", blockPos.getZ());
        }
    }

    public void tick() {
        super.tick();
        BlockPos blockPos = (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
        if (blockPos == null && !this.world.isClient) {
            blockPos = this.getBlockPos();
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
        }

        if (blockPos != null) {
            this.setPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
        }
    }

    public void updatePosition(double x, double y, double z) {
        super.updatePosition(x, y, z);
        if (this.dataTracker != null && this.age != 0) {
            Optional<BlockPos> optional = (Optional)this.dataTracker.get(ATTACHED_BLOCK);
            Optional<BlockPos> optional2 = Optional.of(new BlockPos(x, y, z));
            if (!optional2.equals(optional)) {
                this.dataTracker.set(ATTACHED_BLOCK, optional2);
                this.velocityDirty = true;
            }

        }
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (ATTACHED_BLOCK.equals(data) && this.world.isClient && !this.hasVehicle()) {
            BlockPos blockPos = this.getAttachedBlock();
            if (blockPos != null) {
                this.setPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
            }
        }

        if (HYPNO_BEAM_TARGET_ID.equals(data)) {
            this.beamTicks = 0;
            this.cachedBeamTarget = null;
        }

        super.onTrackedDataSet(data);
    }

    @Nullable
    public BlockPos getAttachedBlock() {
        return (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
    }

    static {
        ATTACHED_BLOCK = DataTracker.registerData(HypnoshroomEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    }

    public void move(MovementType type, Vec3d movement) {
        if (type == MovementType.SHULKER_BOX) {
            this.damage(DamageSource.GENERIC, 9999);
        } else {
            super.move(type, movement);
        }

    }

    public boolean collides() {
        return true;
    }

    public boolean handleAttack(Entity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)attacker;
            return this.damage(DamageSource.player(playerEntity), 9999.0F);
        } else {
            return false;
        }
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        if (fallDistance > 0F) {
            this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
            this.damage(DamageSource.GENERIC, 9999);
        }
        this.playBlockFallSound();
        return true;
    }

    protected boolean canClimb() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void initGoals() {
        this.goalSelector.add(1, new HypnoshroomEntity.FireBeamGoal(this));
        this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof BrowncoatEntity; }));
        this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof ConeheadEntity; }));
        this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof PoleVaultingEntity; }));
        this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof BackupDancerEntity; }));
        this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof NewspaperEntity; }));
        this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof BucketheadEntity; }));
        this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof ScreendoorEntity; }));
        this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof DancingZombieEntity; }));
        this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof FlagzombieEntity; }));
        this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof FootballEntity; }));
        this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
        { return livingEntity instanceof BerserkerEntity; }));
    }

    public static DefaultAttributeContainer.Builder createHypnoshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SNOW_GOLEM_FLAGS, (byte)16);
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
        this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
    }

    public boolean hurtByWater() {
        return false;
    }

    public int getWarmupTime() {
        return 10;
    }

    private void setBeamTarget(int entityId) {
        this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
    }

    public boolean hasBeamTarget() {
        return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
    }

    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        } else if (this.world.isClient) {
            if (this.cachedBeamTarget != null) {
                return this.cachedBeamTarget;
            } else {
                Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID));
                if (entity instanceof LivingEntity) {
                    this.cachedBeamTarget = (LivingEntity)entity;
                    return this.cachedBeamTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    public float getBeamProgress(float tickDelta) {
        return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.heal(1.0F);
            this.healingTime = 6000;
        }

        if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.damage(DamageSource.GENERIC, 9999);
        }

        if (this.hasBeamTarget()) {
            if (this.beamTicks < this.getWarmupTime()) {
                ++this.beamTicks;
            }

            LivingEntity livingEntity = this.getBeamTarget();
            if (livingEntity != null) {
                this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
                this.getLookControl().tick();
                double d = (double)this.getBeamProgress(0.0F);
                double e = livingEntity.getX() - this.getX();
                double f = livingEntity.getBodyY(0.5D) - this.getEyeY();
                double g = livingEntity.getZ() - this.getZ();
                double h = Math.sqrt(e * e + f * f + g * g);
                e /= h;
                f /= h;
                g /= h;
                double j = this.random.nextDouble();

                while(j < h) {
                    j += 1.8D - d + this.random.nextDouble() * (1.7D - d);
                    this.world.addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + e * j, this.getEyeY() + f * j, this.getZ() + g * j, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 13) {
            this.isTired = true;
        }
        else if (status == 12) {
            this.isTired = false;
        }
    }

    protected void mobTick() {
        float f = this.getLightLevelDependentValue();
        if (f > 0.5f) {
            this.isAsleep = true;
            this.world.sendEntityStatus(this, (byte) 13);
        }
        else {
            this.isAsleep = false;
            this.world.sendEntityStatus(this, (byte) 12);
        }
        super.mobTick();
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return PvZCubed.ZOMBIEBITEEVENT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return PvZCubed.PLANTPLANTEDEVENT;
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    static {
        SNOW_GOLEM_FLAGS = DataTracker.registerData(HypnoshroomEntity.class, TrackedDataHandlerRegistry.BYTE);
        HYPNO_BEAM_TARGET_ID = DataTracker.registerData(HypnoshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
    }

    static class FireBeamGoal extends Goal {
        private final HypnoshroomEntity hypnoshroom;
        private int beamTicks;

        public FireBeamGoal(HypnoshroomEntity hypnoshroom) {
            this.hypnoshroom = hypnoshroom;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.hypnoshroom.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && (this.hypnoshroom.squaredDistanceTo(this.hypnoshroom.getTarget()) > 9.0D);
        }

        public void start() {
            this.beamTicks = -10;
            this.hypnoshroom.getNavigation().stop();
            this.hypnoshroom.getLookControl().lookAt(this.hypnoshroom.getTarget(), 90.0F, 90.0F);
            this.hypnoshroom.velocityDirty = true;
        }

        public void stop() {
            this.hypnoshroom.setBeamTarget(0);
            this.hypnoshroom.setTarget((LivingEntity)null);
        }

        public void tick() {
            LivingEntity livingEntity = this.hypnoshroom.getTarget();
            this.hypnoshroom.getNavigation().stop();
            this.hypnoshroom.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
            if (!this.hypnoshroom.canSee(livingEntity) || this.hypnoshroom.isAsleep) {
                this.hypnoshroom.setTarget((LivingEntity) null);
            } else {
                ++this.beamTicks;
                if (this.beamTicks == 0) {
                    this.hypnoshroom.setBeamTarget(this.hypnoshroom.getTarget().getId());
                    if (!this.hypnoshroom.isSilent()) {
                        this.hypnoshroom.world.sendEntityStatus(this.hypnoshroom, (byte) 21);
                    }
                } else if (this.beamTicks >= this.hypnoshroom.getWarmupTime()) {
                    float f = 1.0F;
                    livingEntity.damage(DamageSource.magic(this.hypnoshroom, this.hypnoshroom), f);
                    livingEntity.damage(DamageSource.mob(this.hypnoshroom), (float) this.hypnoshroom.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                    ((LivingEntity) livingEntity).addStatusEffect((new StatusEffectInstance(PvZCubed.HYPNOTIZED, 999, 1))); // applies a status effect
                    this.hypnoshroom.setTarget((LivingEntity) null);
                    this.hypnoshroom.remove(RemovalReason.DISCARDED);
                }
                super.tick();
            }
        }
    }
}
