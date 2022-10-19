package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.plants.projectileentity.ShootingRePeaEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;
import java.util.Random;

public class RepeaterEntity extends GolemEntity implements RangedAttackMob, IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Byte> SNOW_GOLEM_FLAGS;
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private String controllerName = "peacontroller";

    public int healingTime;

    public RepeaterEntity(EntityType<? extends RepeaterEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.idle", true));
        return PlayState.CONTINUE;
    }

    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SNOW_GOLEM_FLAGS, (byte)16);
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
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
            this.resetPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
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
                this.resetPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
            }
        }

        super.onTrackedDataSet(data);
    }

    @Nullable
    public BlockPos getAttachedBlock() {
        return (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
    }

    static {
        ATTACHED_BLOCK = DataTracker.registerData(RepeaterEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    }

    public void move(MovementType type, Vec3d movement) {
        if (type == MovementType.SHULKER_BOX) {
            this.damage(DamageSource.GENERIC, 9999);
        } else {
            super.move(type, movement);
        }

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
            this.playSound(ExampleMod.PLANTPLANTEDEVENT, 0.4F, 1.0F);
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
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(30) + 25, 15.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 15.0F));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    public static DefaultAttributeContainer.Builder createRepeaterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (!this.isInsideWaterOrBubbleColumn()) {
            ShootingRePeaEntity shootingRePeaEntity = new ShootingRePeaEntity(this.world, this);
            double d = this.squaredDistanceTo(target);
            double e = target.getX() - this.getX();
            double f = target.getBodyY(0.5D) - this.getBodyY(0.5D);
            double g = target.getZ() - this.getZ();
            float h = MathHelper.sqrt(MathHelper.sqrt(d)) * 0.5F;
            shootingRePeaEntity.setVelocity(e * (double)h, f * (double)h, g * (double)h, 2.2F, 0F);
            shootingRePeaEntity.updatePosition(shootingRePeaEntity.getX(), this.getY() + 1D, shootingRePeaEntity.getZ());
            if (target.isAlive()) {
                this.playSound(ExampleMod.REPEASHOOTEVENT, 0.3F, 1);
                this.world.spawnEntity(shootingRePeaEntity);
            }
        }
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 15, this::predicate);

        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.60F;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ExampleMod.ZOMBIEBITEEVENT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return ExampleMod.PLANTPLANTEDEVENT;
    }

    public static boolean canRepeaterSpawn(EntityType<RepeaterEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 60;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.intersectsEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    static {
        SNOW_GOLEM_FLAGS = DataTracker.registerData(RepeaterEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}