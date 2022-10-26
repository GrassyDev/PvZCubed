package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
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

import java.util.EnumSet;
import java.util.Random;

public class RepeaterEntity extends AppeaseEntity implements RangedAttackMob, IAnimatable {
	public AnimationFactory factory = new AnimationFactory(this);
	private String controllerName = "peacontroller";

	public int healingTime;

	public boolean isFiring;

	public RepeaterEntity(EntityType<? extends RepeaterEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.healingTime = 6000;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.shoot2", false));
		} else {
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
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
	}

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
	}

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}

	public boolean collides() {
		return true;
	}

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
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
		this.goalSelector.add(1, new RepeaterEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(40) + 35, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
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

	protected void initDataTracker() {
		super.initDataTracker();
	}

	public boolean hurtByWater() {
		return false;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
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
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
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
		return new Vec3d(0.0D, (double) (0.75F * this.getStandingEyeHeight()), (double) (this.getWidth() * 0.4F));
	}

	public static boolean canSnRepeaterSpawn(EntityType<RepeaterEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
		return pos.getY() > 60;
	}

	@Override
	public boolean canSpawn(WorldView worldreader) {
		return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
		}
	}


	static class FireBeamGoal extends Goal {
		private final RepeaterEntity repeaterEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(RepeaterEntity repeaterEntity) {
			this.repeaterEntity = repeaterEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.repeaterEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.repeaterEntity.getNavigation().stop();
			this.repeaterEntity.getLookControl().lookAt(this.repeaterEntity.getTarget(), 90.0F, 90.0F);
			this.repeaterEntity.velocityDirty = true;
		}

		public void stop() {
			this.repeaterEntity.world.sendEntityStatus(this.repeaterEntity, (byte) 10);
			this.repeaterEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.repeaterEntity.getTarget();
			this.repeaterEntity.getNavigation().stop();
			this.repeaterEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.repeaterEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.repeaterEntity.setTarget((LivingEntity) null);
			} else {
				this.repeaterEntity.world.sendEntityStatus(this.repeaterEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.repeaterEntity.isInsideWaterOrBubbleColumn()) {
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.repeaterEntity.world);
						double d = this.repeaterEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.repeaterEntity.getX();
						double f = livingEntity.getBodyY(0.5D) - this.repeaterEntity.getBodyY(0.5D);
						double g = livingEntity.getZ() - this.repeaterEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.2F, 0F);
						proj.updatePosition(this.repeaterEntity.getX(), this.repeaterEntity.getY() + 0.75D, this.repeaterEntity.getZ());
						proj.setOwner(this.repeaterEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.repeaterEntity.world.sendEntityStatus(this.repeaterEntity, (byte) 11);
							this.repeaterEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.repeaterEntity.world.spawnEntity(proj);
						}
					}
				}
				if (this.beamTicks >= 0 && this.animationTicks == -9) {
					ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.repeaterEntity.world);
					double d = this.repeaterEntity.squaredDistanceTo(livingEntity);
					float df = (float) d;
					double e = livingEntity.getX() - this.repeaterEntity.getX();
					double f = livingEntity.getBodyY(0.5D) - this.repeaterEntity.getBodyY(0.5D);
					double g = livingEntity.getZ() - this.repeaterEntity.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.2F, 0F);
					proj.updatePosition(this.repeaterEntity.getX(), this.repeaterEntity.getY() + 0.75D, this.repeaterEntity.getZ());
					if (livingEntity.isAlive()) {
						this.beamTicks = -7;
						this.repeaterEntity.world.sendEntityStatus(this.repeaterEntity, (byte) 11);
						this.repeaterEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
						this.repeaterEntity.world.spawnEntity(proj);
					}
				}
				else if (this.animationTicks >= 0) {
					this.repeaterEntity.world.sendEntityStatus(this.repeaterEntity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
