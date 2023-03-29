package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.navybean;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit.SpitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
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

public class NavyBeanEntity extends PlantEntity implements IAnimatable, RangedAttackMob {


	private int amphibiousRaycastDelay;

	private boolean dryLand;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private int attackTicksLeft;
	private String controllerName = "beancontroller";
	public boolean isFiring;

	public NavyBeanEntity(EntityType<? extends NavyBeanEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
	}

	public NavyBeanEntity(World world, double x, double y, double z) {
		this(PvZEntity.NAVYBEAN, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			this.attackTicksLeft = 20;
		} else {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION~//~*~//
	 **/

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
		int i = this.attackTicksLeft;
		if (this.dryLand) {
			if (this.isFiring) {
				event.getController().setAnimation(new AnimationBuilder().loop("navybean.shoot"));
			} else if (i <= 0) {
				event.getController().setAnimation(new AnimationBuilder().loop("navybean.idle"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().playOnce("navybean.hit"));
			}
		}
		else {
			if (this.isFiring) {
				event.getController().setAnimation(new AnimationBuilder().loop("navybean.shoot2"));
			} else if (i <= 0) {
				event.getController().setAnimation(new AnimationBuilder().loop("navybean.idle2"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().playOnce("navybean.hit2"));
			}
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~AI~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new NavyBeanEntity.FireBeamGoal(this));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!generalPvZombieEntity.isFlying();
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		snorkelGoal();
	}

	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel() && !(snorkelEntity.getHypno());
		}));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
		if (target.squaredDistanceTo(this) <= 25) {
			this.tryAttack(target);
		}
	}

	public boolean tryAttack(Entity target) {
		int i = this.attackTicksLeft;
		ZombiePropEntity passenger = null;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				passenger = zpe;
			}
		}
		Entity damaged = target;
		if (passenger != null) {
			damaged = passenger;
		}
		if (i <= 0) {
			this.attackTicksLeft = 20;
			this.world.sendEntityStatus(this, (byte) 106);
			boolean bl = damaged.damage(DamageSource.mob(this), this.getAttackDamage());
			if (bl) {
				this.applyDamageEffects(this, target);
			}
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZCubed.BUCKETHITEVENT;
				case "plastic" -> PvZCubed.CONEHITEVENT;
				case "stone" -> PvZCubed.STONEHITEVENT;
				default -> PvZCubed.PEAHITEVENT;
			};
			target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
			this.chomperAudioDelay = 3;
			return bl;
		} else {
			return false;
		}
	}


	/**
	 * //~*~//~POSITION~//~*~//
	 **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		if (--this.chomperAudioDelay == 0) {
			this.playSound(PvZCubed.PEASHOOTEVENT, 1.0F, 1.0F);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		LivingEntity target = this.getTarget();
		if (target != null) {
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (--amphibiousRaycastDelay >= 0) {
			amphibiousRaycastDelay = 60;
			HitResult hitResult = amphibiousRaycast(0.25);
			if (hitResult.getType() == HitResult.Type.MISS) {
				kill();
			}
			if (this.age != 0) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = world.getFluidState(this.getBlockPos().add(0, -0.5, 0));
				if (!(fluidState.getFluid() == Fluids.WATER)) {
					this.dryLand = true;
					onWater = false;
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.NAVYBEAN_SEED_PACKET);
				}
					this.kill();
					kill();
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}


	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.NAVYBEAN_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createNavyBeanAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D);
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

	private float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
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

	public void onDeath(DamageSource source) {
		super.onDeath(source);
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


	/**
	 * //~*~//~DAMAGE HANDLER~//~*~//
	 **/

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
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}


	/**
	 * /~*~//~*GOALS*~//~*~/
	 **/

	static class FireBeamGoal extends Goal {
		private final NavyBeanEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(NavyBeanEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && livingEntity.squaredDistanceTo(plantEntity) > 25 && !plantEntity.dryLand;
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			if (plantEntity.getTarget() != null){
				this.plantEntity.attack(plantEntity.getTarget(), 0);
			}
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						SpitEntity proj = new SpitEntity(PvZEntity.SPIT, this.plantEntity.world);
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float) d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						proj.setYaw(this.plantEntity.getYaw());
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.world.spawnEntity(proj);
						}
					}
				} else if (this.animationTicks >= 0) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
