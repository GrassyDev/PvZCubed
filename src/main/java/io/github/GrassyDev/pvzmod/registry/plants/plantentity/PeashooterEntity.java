package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

/*
 * Our Peashooter Entity extends PathAwareEntity, which extends MobEntity, which extends LivingEntity.
 *
 * LivingEntity has health and can deal damage.
 * MobEntity has movement controls and AI capabilities.
 * PathAwareEntity has pathfinding favor and slightly tweaked leash behavior.
 */
public class PeashooterEntity extends AppeaseEntity implements IAnimatable, RangedAttackMob {
    public AnimationFactory factory = new AnimationFactory(this);

    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
    private String controllerName = "peacontroller";
    public int healingTime;

	public boolean isFiring;

    public PeashooterEntity(EntityType<? extends PeashooterEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.shoot", false));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.idle", true));
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

        super.onTrackedDataSet(data);
    }

    @Nullable
    public BlockPos getAttachedBlock() {
        return (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
    }

    static {
        ATTACHED_BLOCK = DataTracker.registerData(PeashooterEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
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
		this.goalSelector.add(1, new PeashooterEntity.FireBeamGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    public static DefaultAttributeContainer.Builder createPeashooterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
    }

    public boolean hurtByWater() {
        return false;
    }

	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
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

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.60F;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 1, this::predicate);

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

    public static boolean canPeashooterSpawn(EntityType<PeashooterEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 60;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

	static class FireBeamGoal extends Goal {
		private final PeashooterEntity peashooterEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PeashooterEntity peashooterEntity) {
			this.peashooterEntity = peashooterEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.peashooterEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.peashooterEntity.getNavigation().stop();
			this.peashooterEntity.getLookControl().lookAt(this.peashooterEntity.getTarget(), 90.0F, 90.0F);
			this.peashooterEntity.velocityDirty = true;
		}

		public void stop() {
			this.peashooterEntity.world.sendEntityStatus(this.peashooterEntity, (byte) 10);
			this.peashooterEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.peashooterEntity.getTarget();
			this.peashooterEntity.getNavigation().stop();
			this.peashooterEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.peashooterEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.peashooterEntity.setTarget((LivingEntity) null);
			} else {
				this.peashooterEntity.world.sendEntityStatus(this.peashooterEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.peashooterEntity.isInsideWaterOrBubbleColumn()) {
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.peashooterEntity.world);
						double d = this.peashooterEntity.squaredDistanceTo(livingEntity);
						float df = (float)d;
						double e = livingEntity.getX() - this.peashooterEntity.getX();
						double f = livingEntity.getBodyY(0.5D) - this.peashooterEntity.getBodyY(0.5D);
						double g = livingEntity.getZ() - this.peashooterEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 2.2F, 0F);
						proj.updatePosition(this.peashooterEntity.getX(), this.peashooterEntity.getY() + 0.75D, this.peashooterEntity.getZ());
						proj.setOwner(this.peashooterEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.peashooterEntity.world.sendEntityStatus(this.peashooterEntity, (byte) 11);
							this.peashooterEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peashooterEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.peashooterEntity.world.sendEntityStatus(this.peashooterEntity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
