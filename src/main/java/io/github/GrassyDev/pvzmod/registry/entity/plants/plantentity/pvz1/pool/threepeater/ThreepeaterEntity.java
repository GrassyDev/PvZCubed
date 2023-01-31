package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;
public class ThreepeaterEntity extends AppeaseEntity implements IAnimatable, RangedAttackMob {

	private String controllerName = "threepeacontroller";

    public int healingTime;

	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ThreepeaterEntity(EntityType<? extends ThreepeaterEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
    }

	static  {
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
			event.getController().setAnimation(new AnimationBuilder().playOnce("threepeater.shoot"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("threepeater.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new ThreepeaterEntity.FireBeamGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(45) + 30, 17.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 18.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity) && !(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel());
		}));
		snorkelGoal();
    }

	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel();
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
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
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
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
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

	public static DefaultAttributeContainer.Builder createThreepeaterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
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

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
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

	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final ThreepeaterEntity threepeaterentity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ThreepeaterEntity threepeaterEntity) {
			this.threepeaterentity = threepeaterEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.threepeaterentity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.threepeaterentity.getNavigation().stop();
			this.threepeaterentity.getLookControl().lookAt(this.threepeaterentity.getTarget(), 90.0F, 90.0F);
			this.threepeaterentity.velocityDirty = true;
		}

		public void stop() {
			this.threepeaterentity.world.sendEntityStatus(this.threepeaterentity, (byte) 10);
			this.threepeaterentity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.threepeaterentity.getTarget();
			this.threepeaterentity.getNavigation().stop();
			this.threepeaterentity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.threepeaterentity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.threepeaterentity.setTarget((LivingEntity) null);
			} else {
				this.threepeaterentity.world.sendEntityStatus(this.threepeaterentity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -9) {
					double time = (this.threepeaterentity.squaredDistanceTo(livingEntity) > 6) ? 50 : 1;
					Vec3d targetPos = livingEntity.getPos();
					Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
					if (!this.threepeaterentity.isInsideWaterOrBubbleColumn()) {
						// Middle Pea
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.threepeaterentity.world);
						double d = this.threepeaterentity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.threepeaterentity.getX();
						double f = livingEntity.getY() - this.threepeaterentity.getY();
						double g = predictedPos.getZ() - this.threepeaterentity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.threepeaterentity.getX(), this.threepeaterentity.getY() + 0.8D, this.threepeaterentity.getZ());
						proj.setOwner(this.threepeaterentity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -16;
							this.threepeaterentity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.threepeaterentity.world.spawnEntity(proj);
						}
						// Right Pea
						ShootingPeaEntity proj3 = new ShootingPeaEntity(PvZEntity.PEA, this.threepeaterentity.world);
						Vec3d vec3d3 = this.threepeaterentity.getRotationVec(1.0F).rotateY(-90);
						double d3 = this.threepeaterentity.squaredDistanceTo(predictedPos);
						float df3 = (float) d3;
						double e3 = predictedPos.getX() - this.threepeaterentity.getX();
						double f3 = livingEntity.getY() - this.threepeaterentity.getY();
						double g3 = predictedPos.getZ() - this.threepeaterentity.getZ();
						float h3 = MathHelper.sqrt(MathHelper.sqrt(df3)) * 0.5F;
						proj3.setVelocity(e3 + vec3d3.x * 1.125, f3 * (double) h3, g3 + vec3d3.z * 1.125, 0.33F, 0F);
						proj3.updatePosition(this.threepeaterentity.getX() + vec3d3.x * 0.55, this.threepeaterentity.getY() + 0.5, this.threepeaterentity.getZ() + vec3d3.z * 0.55);
						proj3.setOwner(this.threepeaterentity);
						if (livingEntity.isAlive()) {
							this.threepeaterentity.world.sendEntityStatus(this.threepeaterentity, (byte) 11);
							this.threepeaterentity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.threepeaterentity.world.spawnEntity(proj3);
						}
						// Left Pea
						ShootingPeaEntity proj2 = new ShootingPeaEntity(PvZEntity.PEA, this.threepeaterentity.world);
						Vec3d vec3d2 = this.threepeaterentity.getRotationVec(1.0F).rotateY(90);
						double d2 = this.threepeaterentity.squaredDistanceTo(predictedPos);
						float df2 = (float) d2;
						double e2 = predictedPos.getX() - this.threepeaterentity.getX();
						double f2 = livingEntity.getY() - this.threepeaterentity.getY();
						double g2 = predictedPos.getZ() - this.threepeaterentity.getZ();
						float h2 = MathHelper.sqrt(MathHelper.sqrt(df2)) * 0.5F;
						proj2.setVelocity(e2 + vec3d2.x * 1.125, f2 * (double) h2, g2 + vec3d2.z * 1.125, 0.33F, 0);
						proj2.updatePosition(this.threepeaterentity.getX() + vec3d2.x * 0.55, this.threepeaterentity.getY() + 0.5, this.threepeaterentity.getZ() + vec3d2.z * 0.55);
						proj2.setOwner(this.threepeaterentity);
						if (livingEntity.isAlive()) {
							this.threepeaterentity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.threepeaterentity.world.spawnEntity(proj2);
						}
					}
				} else if (this.animationTicks >= 0) {
					this.threepeaterentity.world.sendEntityStatus(this.threepeaterentity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
