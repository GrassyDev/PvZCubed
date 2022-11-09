package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gatlingpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class GatlingpeaEntity extends AppeaseEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "peacontroller";

    public int healingTime;

	public boolean isFiring;

	public AnimationFactory factory = new AnimationFactory(this);

    public GatlingpeaEntity(EntityType<? extends GatlingpeaEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
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


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.gatling.shoot", false));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peashooter.idle", true));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new GatlingpeaEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

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


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.heal(4.0F);
			this.healingTime = 6000;
		}

		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.damage(DamageSource.GENERIC, 9999);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGatlingpeaAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 90.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZCubed.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

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


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canGatlingpeaSpawn(EntityType<GatlingpeaEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 60;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final GatlingpeaEntity gatlingpeaEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(GatlingpeaEntity gatlingpeaEntity) {
			this.gatlingpeaEntity = gatlingpeaEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.gatlingpeaEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -6;
			this.animationTicks = -16;
			this.gatlingpeaEntity.getNavigation().stop();
			this.gatlingpeaEntity.getLookControl().lookAt(this.gatlingpeaEntity.getTarget(), 90.0F, 90.0F);
			this.gatlingpeaEntity.velocityDirty = true;
		}

		public void stop() {
			this.gatlingpeaEntity.world.sendEntityStatus(this.gatlingpeaEntity, (byte) 10);
			this.gatlingpeaEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.gatlingpeaEntity.getTarget();
			this.gatlingpeaEntity.getNavigation().stop();
			this.gatlingpeaEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.gatlingpeaEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.gatlingpeaEntity.setTarget((LivingEntity) null);
			} else {
				this.gatlingpeaEntity.world.sendEntityStatus(this.gatlingpeaEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -6) {
					if (!this.gatlingpeaEntity.isInsideWaterOrBubbleColumn()) {
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.gatlingpeaEntity.world);
						double d = this.gatlingpeaEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.gatlingpeaEntity.getX();
						double f = livingEntity.getY() - this.gatlingpeaEntity.getY();
						double g = livingEntity.getZ() - this.gatlingpeaEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.gatlingpeaEntity.getX(), this.gatlingpeaEntity.getY() + 0.75D, this.gatlingpeaEntity.getZ());
						proj.setOwner(this.gatlingpeaEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.gatlingpeaEntity.world.sendEntityStatus(this.gatlingpeaEntity, (byte) 11);
							this.gatlingpeaEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.gatlingpeaEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks == -3) {
					ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.gatlingpeaEntity.world);
					double d = this.gatlingpeaEntity.squaredDistanceTo(livingEntity);
					float df = (float) d;
					double e = livingEntity.getX() - this.gatlingpeaEntity.getX();
					double f = livingEntity.getY() - this.gatlingpeaEntity.getY();
					double g = livingEntity.getZ() - this.gatlingpeaEntity.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
					proj.updatePosition(this.gatlingpeaEntity.getX(), this.gatlingpeaEntity.getY() + 0.75D, this.gatlingpeaEntity.getZ());
					proj.setOwner(this.gatlingpeaEntity);
					if (livingEntity.isAlive()) {
						this.gatlingpeaEntity.world.sendEntityStatus(this.gatlingpeaEntity, (byte) 11);
						this.gatlingpeaEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
						this.gatlingpeaEntity.world.spawnEntity(proj);
					}
				}
				else if (this.animationTicks >= 0) {
					this.gatlingpeaEntity.world.sendEntityStatus(this.gatlingpeaEntity, (byte) 10);
					this.beamTicks = -6;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
