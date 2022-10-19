package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.plants.projectileentity.SporeEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
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
import java.util.Random;

public class ScaredyshroomEntity extends GolemEntity implements IAnimatable, RangedAttackMob {
    public AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Byte> SNOW_GOLEM_FLAGS;
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private String controllerName = "scaredycontroller";
    public boolean isAsleep;
    public boolean isTired;
    public int healingTime;
    public boolean isAfraid;
    public boolean isScared;
    public int animationScare;

    public ScaredyshroomEntity(EntityType<? extends ScaredyshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
        this.isScared = false;
        this.animationScare = 30;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isTired) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.asleep", true));
        }
        else if (this.animationScare <= 0 && this.isAfraid){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.afraid", true));
        }
        else if (this.isAfraid){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.hiding", false));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.idle", true));
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
        BlockPos blockPos = (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
        if (blockPos == null && !this.world.isClient) {
            blockPos = this.getBlockPos();
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
        }

        if (blockPos != null) {
            this.resetPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
        }

        super.tick();
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
        ATTACHED_BLOCK = DataTracker.registerData(ScaredyshroomEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
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
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(45) + 40, 30.0F));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    public static DefaultAttributeContainer.Builder createScaredyshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SNOW_GOLEM_FLAGS, (byte)16);
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
    }

    public boolean hurtByWater() {
        return false;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        double t = this.squaredDistanceTo(target);
        if (!this.isAsleep && !this.isAfraid) {
            if (t < 36) {
                this.isScared = true;
            }
            else if (!this.isInsideWaterOrBubbleColumn()) {
                this.isScared = false;
                SporeEntity sporeEntity = new SporeEntity(this.world, this);
                double d = this.squaredDistanceTo(target);
                double e = target.getX() - this.getX();
                double f = target.getBodyY(0.5D) - this.getBodyY(0.5D);
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(MathHelper.sqrt(d)) * 0.5F;
                sporeEntity.setVelocity(e * (double)h, f * (double)h, g * (double)h, 4.45F, 0F);
                sporeEntity.updatePosition(sporeEntity.getX(), this.getY() + 1D, sporeEntity.getZ());

                if (target.isAlive()) {
                    this.playSound(ExampleMod.MUSHROOMSHOOTEVENT, 0.3F, 1);
                    this.world.spawnEntity(sporeEntity);
                }
            }
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

        if (this.animationScare > 0 && this.isAfraid) {
            --this.animationScare;
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
        if (status == 4) {
            this.isAfraid = true;
        }
        if (status == 5) {
            this.isAfraid = false;
            this.animationScare = 30;
        }
    }

    protected void mobTick() {
        float f = this.getBrightnessAtEyes();
        if (f > 0.5f) {
            this.isAsleep = true;
            this.world.sendEntityStatus(this, (byte) 13);
        }
        else {
            this.isAsleep = false;
            this.world.sendEntityStatus(this, (byte) 12);
        }
        if (this.isScared) {
            this.world.sendEntityStatus(this, (byte) 4);
        }
        else {
            this.world.sendEntityStatus(this, (byte) 5);
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
        return ExampleMod.ZOMBIEBITEEVENT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return ExampleMod.PLANTPLANTEDEVENT;
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    public static boolean isSpawnDark(ServerWorldAccess serverWorldAccess, BlockPos pos, Random random) {
        if (serverWorldAccess.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = serverWorldAccess.toServerWorld().isThundering() ? serverWorldAccess.getLightLevel(pos, 10) : serverWorldAccess.getLightLevel(pos);
            return i <= random.nextInt(11);
        }
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.intersectsEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    static {
        SNOW_GOLEM_FLAGS = DataTracker.registerData(ScaredyshroomEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}