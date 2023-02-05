package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage.ShootingCabbageEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
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
import net.minecraft.item.ItemStack;
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

public class CabbagepultEntity extends AppeaseEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "peacontroller";

    public int healingTime;

	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public CabbagepultEntity(EntityType<? extends CabbagepultEntity> entityType, World world) {
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
			event.getController().setAnimation(new AnimationBuilder().playOnce("cabbagepult.shoot"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("cabbagepult.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new CabbagepultEntity.FireBeamGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 30.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity;
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof NewspaperEntity;
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof ScreendoorEntity;
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
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
			if (target.getHealth() <= 0) {
				this.setTarget(null);
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.damage(DamageSource.GENERIC, 9999);
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CABBAGEPULT_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createCabbagePultAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZCubed.ZOMBIEBITEEVENT;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZCubed.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

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
		private final CabbagepultEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(CabbagepultEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -16;
			this.animationTicks = -32;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 10);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -16) {
					// Huge thanks to Forrest Smith(forrestthewoods) for the trajectory code (https://www.forrestthewoods.com/blog/solving_ballistic_trajectories/)
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						ShootingCabbageEntity proj = new ShootingCabbageEntity(PvZEntity.CABBAGE, this.plantEntity.world);
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 6) ? 50 : 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = predictedPos.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						Vec3d projPos = new Vec3d(this.plantEntity.getX(), this.plantEntity.getY() + 1.75D, this.plantEntity.getZ());
						Vec3d vel = this.plantEntity.solve_ballistic_arc_lateral(projPos, 1F, predictedPos, 5);
						System.out.println(f);
						proj.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(),1F, 0F);
						proj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
						proj.setOwner(this.plantEntity);
						if (plantEntity.getTarget() != null){
							proj.getTarget(plantEntity.getTarget());
						}
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 11);
							this.plantEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.plantEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 10);
					this.beamTicks = -16;
					this.animationTicks = -32;
				}
				super.tick();
			}
		}
	}

	public Vec3d fire_velocity;


	// Solve the firing arc with a fixed lateral speed. Vertical speed and gravity varies.
	// This enables a visually pleasing arc.
	//
	// proj_pos (Vector3): point projectile will fire from
	// lateral_speed (float): scalar speed of projectile along XZ plane
	// target_pos (Vector3): point projectile is trying to hit
	// max_height (float): height above Max(proj_pos, impact_pos) for projectile to peak at
	//
	// fire_velocity (out Vector3): firing velocity
	// gravity (out float): gravity necessary to projectile to hit precisely max_height
	//
	// return (bool): true if a valid solution was found
	public Vec3d solve_ballistic_arc_lateral(Vec3d proj_pos, float lateral_speed, Vec3d target_pos, float max_height) {

		Vec3d diff = target_pos.subtract(proj_pos);
		Vec3d diffXZ = new Vec3d(diff.x, 0f, diff.z);
		float lateralDist = (float) diffXZ.length();

		if (lateralDist == 0)
			return fire_velocity = Vec3d.ZERO;

		float time = lateralDist / lateral_speed;

		fire_velocity = diffXZ.normalize().multiply(lateral_speed);

		// System of equations. Hit max_height at t=.5*time. Hit target at t=time.
		//
		// peak = y0 + vertical_speed*halfTime + .5*gravity*halfTime^2
		// end = y0 + vertical_speed*time + .5*gravity*time^s
		// Wolfram Alpha: solve b = a + .5*v*t + .5*g*(.5*t)^2, c = a + vt + .5*g*t^2 for g, v
		float a = (float) proj_pos.y;       // initial
		float b = max_height;       // peak
		float c = (float) target_pos.y;     // final

		return fire_velocity.subtract(0, -(3*a - 4*b + c) / time, 0);
	}

	/**static class FireBeamGoal extends Goal {
		private final CabbagepultEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(CabbagepultEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -16;
			this.animationTicks = -32;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 10);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -16) {
					// Huge thanks to Forrest Smith(forrestthewoods) for the trajectory code (https://www.forrestthewoods.com/blog/solving_ballistic_trajectories/)
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						ShootingCabbageEntity proj = new ShootingCabbageEntity(PvZEntity.CABBAGE, this.plantEntity.world);
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 6) ? 50 : 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						Vec3d projPos = new Vec3d(this.plantEntity.getX(), this.plantEntity.getY() + 1.75D, this.plantEntity.getZ());
						Vec3d vel = this.plantEntity.solve_ballistic_arc(projPos, 1F, targetPos, livingEntity.getVelocity(), 9.8F);
						proj.setVelocity(vel.getX(), vel.getY() * h, vel.getZ(), 1F, 0F);
						proj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
						proj.setOwner(this.plantEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 11);
							this.plantEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.plantEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 10);
					this.beamTicks = -16;
					this.animationTicks = -32;
				}
				super.tick();
			}
		}
	}

	Vec3d s0;
	Vec3d s1;

	public Vec3d solve_ballistic_arc(Vec3d proj_pos, float proj_speed, Vec3d target_pos, Vec3d target_velocity, float gravity) {

		// Initialize output parameters
		s0 = Vec3d.ZERO;
		s1 = Vec3d.ZERO;

		// Derivation
		//
		//  For full derivation see: blog.forrestthewoods.com
		//  Here is an abbreviated version.
		//
		//  Four equations, four unknowns (solution.x, solution.y, solution.z, time):
		//
		//  (1) proj_pos.x + solution.x*time = target_pos.x + target_vel.x*time
		//  (2) proj_pos.y + solution.y*time + .5*G*t = target_pos.y + target_vel.y*time
		//  (3) proj_pos.z + solution.z*time = target_pos.z + target_vel.z*time
		//  (4) proj_speed^2 = solution.x^2 + solution.y^2 + solution.z^2
		//
		//  (5) Solve for solution.x and solution.z in equations (1) and (3)
		//  (6) Square solution.x and solution.z from (5)
		//  (7) Solve solution.y^2 by plugging (6) into (4)
		//  (8) Solve solution.y by rearranging (2)
		//  (9) Square (8)
		//  (10) Set (8) = (7). All solution.xyz terms should be gone. Only time remains.
		//  (11) Rearrange 10. It will be of the form a*^4 + b*t^3 + c*t^2 + d*t * e. This is a quartic.
		//  (12) Solve the quartic using SolveQuartic.
		//  (13) If there are no positive, real roots there is no solution.
		//  (14) Each positive, real root is one valid solution
		//  (15) Plug each time value into (1) (2) and (3) to calculate solution.xyz
		//  (16) The end.

		double G = gravity;

		double A = proj_pos.x;
		double B = proj_pos.y;
		double C = proj_pos.z;
		double M = target_pos.x;
		double N = target_pos.y;
		double O = target_pos.z;
		double P = target_velocity.x;
		double Q = target_velocity.y;
		double R = target_velocity.z;
		double S = proj_speed;

		double H = M - A;
		double J = O - C;
		double K = N - B;
		double L = -.5f * G;

		// Quartic Coeffecients
		double c0 = L*L;
		double c1 = -2*Q*L;
		double c2 = Q*Q - 2*K*L - S*S + P*P + R*R;
		double c3 = 2*K*Q + 2*H*P + 2*J*R;
		double c4 = K*K + H*H + J*J;

		// Solve quartic
		double[] times = new double[4];



		double[] numTimes = PvZCubed.SolveQuartic(c0, c1, c2, c3, c4);

		times[0] = numTimes[0];
		times[1] = numTimes[1];
		times[2] = numTimes[2];
		times[3] = numTimes[3];



		// Sort so faster collision is found first
		Arrays.sort(times);

		// Plug quartic solutions into base equations
		// There should never be more than 2 positive, real roots.
		Vec3d[] solutions = new Vec3d[2];
		int numSolutions = 0;

		for (int i = 0; i < times.length && numSolutions < 2; ++i) {
			double t = times[i];
			if (t <= 0 || Double.isNaN(t))
				continue;

			solutions[numSolutions] = new Vec3d ((float)((H+P*t)/t), (float)((K+Q*t-L*t*t)/ t), (float)((J+R*t)/t));
			++numSolutions;
		}

		// Write out solutions
		if (numSolutions > 0) {
			s0 = solutions[0];
			return s0;
		}
		if (numSolutions > 1) {
			s1 = solutions[1];
			return s1;
		}
		else {
			return s0;
		}
	}**/
}
