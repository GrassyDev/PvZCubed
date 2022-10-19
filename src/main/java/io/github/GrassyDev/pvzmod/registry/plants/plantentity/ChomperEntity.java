package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.ScreendoorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;

public class ChomperEntity extends GolemEntity implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "chompcontroller";
    public int healingTime;
    protected static final TrackedData<Byte> IRON_GOLEM_FLAGS;
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;

    public ChomperEntity(EntityType<? extends ChomperEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 600;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        int i = this.attackTicksLeft;
        if (this.eatingShield) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp3", false));
        }
        else if (i <= 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.idle", true));
        }
        else if (this.notEating) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp2", false));
        }
        else if (i > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp", false));
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

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("APX")) {
            int i = nbt.getInt("APX");
            int j = nbt.getInt("APY");
            int k = nbt.getInt("APZ");
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(new BlockPos(i, j, k)));
        } else {
            this.dataTracker.set(ATTACHED_BLOCK, Optional.empty());
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        BlockPos blockPos = this.getAttachedBlock();
        if (blockPos != null) {
            nbt.putInt("APX", blockPos.getX());
            nbt.putInt("APY", blockPos.getY());
            nbt.putInt("APZ", blockPos.getZ());
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

        super.onTrackedDataSet(data);
    }

    @Nullable
    public BlockPos getAttachedBlock() {
        return (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
    }

    static {
        ATTACHED_BLOCK = DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
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

    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
        this.dataTracker.startTracking(IRON_GOLEM_FLAGS, (byte)0);
    }

    public static DefaultAttributeContainer.Builder createChomperAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 999.0D);
    }

    public boolean hurtByWater() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.heal(1.0F);
            this.healingTime = 600;
        }

        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.damage(DamageSource.GENERIC, 9999);
        }

        if (this.attackTicksLeft <= 0){
            this.eatingShield = false;
            this.notEating = false;
        }
    }

    private float getAttackDamage(){
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    public boolean tryAttack(Entity target) {
        int i = this.attackTicksLeft;
        if (target instanceof ScreendoorEntity) {
            if (i <= 0) {
                this.attackTicksLeft = 200;
                this.world.sendEntityStatus(this, (byte) 5);
                float f = 153f;
                boolean bl = target.damage(DamageSource.mob(this), f);
                if (bl) {
                    this.applyDamageEffects(this, target);
                }
                this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
                return bl;
            } else {
                return false;
            }
        }
        else if ((target instanceof BasicGraveEntity) ||
                (target instanceof NightGraveEntity)) {
            if (i <= 0) {
                this.attackTicksLeft = 30;
                this.world.sendEntityStatus(this, (byte) 6);
                float f = 16f;
                boolean bl = target.damage(DamageSource.mob(this), f);
                if (bl) {
                    this.applyDamageEffects(this, target);
                }
                this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
                return bl;
            } else {
                return false;
            }
        }
        else {
            if (i <= 0) {
                this.attackTicksLeft = 200;
                this.world.sendEntityStatus(this, (byte) 4);
                float f = this.getAttackDamage();
                boolean bl = target.damage(DamageSource.mob(this), f);
                if (bl) {
                    this.applyDamageEffects(this, target);
                }
                this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
                return bl;
            } else {
                return false;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackTicksLeft = 200;
            this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
            this.eatingShield = false;
            this.notEating = false;
        }
        if (status == 5) {
            this.attackTicksLeft = 200;
            this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
            this.eatingShield = true;
            this.notEating = false;
        }
        else if (status == 6) {
            this.attackTicksLeft = 30;
            this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
            this.eatingShield = false;
            this.notEating = true;
        }else {
            super.handleStatus(status);
        }
    }

    @Environment(EnvType.CLIENT)
    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.60F;
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

    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    public static boolean canChomperSpawn(EntityType<ChomperEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 60;
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.875F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    static {
        IRON_GOLEM_FLAGS = DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
