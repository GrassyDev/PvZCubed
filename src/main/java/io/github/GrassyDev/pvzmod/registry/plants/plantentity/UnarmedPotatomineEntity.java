package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.registry.PvZEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.world.PvZExplosion;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

@EnvironmentInterfaces({@EnvironmentInterface(
        value = EnvType.CLIENT,
        itf = SkinOverlayOwner.class
)})
public class UnarmedPotatomineEntity extends GolemEntity implements IAnimatable, SkinOverlayOwner {
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "potatocontroller";
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 1;
    private int explosionRadius = 1;
    public int potatoPreparingTime;

    public UnarmedPotatomineEntity(EntityType<? extends UnarmedPotatomineEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.potatoPreparingTime = 600;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("potatomine.unarmed", true));
        return PlayState.CONTINUE;
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
        ATTACHED_BLOCK = DataTracker.registerData(UnarmedPotatomineEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
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
        int i = this.getFuseSpeed();
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    public static DefaultAttributeContainer.Builder createUnarmedPotatomineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 180);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
        this.dataTracker.startTracking(FUSE_SPEED, -1);
        this.dataTracker.startTracking(CHARGED, false);
        this.dataTracker.startTracking(IGNITED, false);
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
        this.dataTracker.set(CHARGED, tag.getBoolean("powered"));
        if (tag.contains("Fuse", 99)) {
            this.fuseTime = tag.getShort("Fuse");
        }

        if (tag.contains("ExplosionRadius", 99)) {
            this.explosionRadius = tag.getByte("ExplosionRadius");
        }

        if (tag.getBoolean("ignited")) {
            this.ignite();
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
        if ((Boolean)this.dataTracker.get(CHARGED)) {
            tag.putBoolean("powered", true);
        }

        tag.putShort("Fuse", (short)this.fuseTime);
        tag.putByte("ExplosionRadius", (byte)this.explosionRadius);
        tag.putBoolean("ignited", this.getIgnited());
    }

    public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.world instanceof ServerWorld)) {
            return false;
        } else {
            ServerWorld serverWorld = (ServerWorld)this.world;
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == DamageSource.OUT_OF_WORLD) {
                this.playSound(ExampleMod.ENTITYRISINGEVENT, 1.5F, 1.0F);
                PotatomineEntity potatomineEntity = (PotatomineEntity) PvZEntity.POTATOMINE.create(world);
                potatomineEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
                potatomineEntity.initialize(serverWorld, world.getLocalDifficulty(potatomineEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                potatomineEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    potatomineEntity.setCustomName(this.getCustomName());
                    potatomineEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                potatomineEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(potatomineEntity);
                this.remove();
            }

            return true;
        }
    }

    public void tick() {
        super.tick();
        if (this.isAlive() && --this.potatoPreparingTime <= 0) {
            this.lastFuseTime = this.currentFuseTime;
            ((LivingEntity) this).addStatusEffect((new StatusEffectInstance(ExampleMod.HYPNOTIZED, 999, 1))); // applies a status effect
            if (this.getIgnited()) {
                this.setFuseSpeed(1);
            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;

            }
        }

        BlockPos blockPos = (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
        if (blockPos == null && !this.world.isClient) {
            blockPos = this.getBlockPos();
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
        }

        if (blockPos != null) {
            this.resetPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
        }
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
    protected SoundEvent getDeathSound() {
        return ExampleMod.PLANTPLANTEDEVENT;
    }

    public boolean tryAttack(Entity target) {
        return true;
    }

    public boolean shouldRenderOverlay() {
        return (Boolean)this.dataTracker.get(CHARGED);
    }

    @Environment(EnvType.CLIENT)
    public float getClientFuseTime(float timeDelta) {
        return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }

    public int getFuseSpeed() {
        return (Integer)this.dataTracker.get(FUSE_SPEED);
    }

    public void setFuseSpeed(int fuseSpeed) {
        this.dataTracker.set(FUSE_SPEED, fuseSpeed);
    }

    public boolean getIgnited() {
        return (Boolean)this.dataTracker.get(IGNITED);
    }

    public void ignite() {
        this.dataTracker.set(IGNITED, true);
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    public static boolean canUnarmedPotatomineSpawn(EntityType<UnarmedPotatomineEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 0 && pos.getY() < 65;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.intersectsEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    static {
        FUSE_SPEED = DataTracker.registerData(UnarmedPotatomineEntity.class, TrackedDataHandlerRegistry.INTEGER);
        CHARGED = DataTracker.registerData(UnarmedPotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        IGNITED = DataTracker.registerData(UnarmedPotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
