package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike.ShootingBeeSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
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
import net.minecraft.sound.SoundEvents;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_STRENGTH;

public class BeeshooterEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "peacontroller";



	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BeeshooterEntity(EntityType<? extends BeeshooterEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

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
			event.getController().setAnimation(new AnimationBuilder().playOnce("peashooter.shoot"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("peashooter.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new BeeshooterEntity.FireBeamGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
					generalPvZombieEntity.isFlying() &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getFirstPassenger() != null &&
					generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno());
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11);
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10);
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9);
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8);
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7);
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6);
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5);
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4);
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3);
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2);
		}));
		this.targetSelector.add(13, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1);
		}));
		this.targetSelector.add(14, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getFirstPassenger() != null &&
					!(generalPvZombieEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())
					&& !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0);
		}));
		this.targetSelector.add(15, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
					&& !(generalPvZombieEntity.getHypno());
		}));
		this.targetSelector.add(16, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
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
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.BEESHOOTER_SEED_PACKET);
				}
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
			else if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
				this.setTarget(null);
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BEESHOOTER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBeeshooterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
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
	protected SoundEvent getHurtSound(DamageSource source) {return PvZCubed.SILENCEVENET;}

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
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final BeeshooterEntity beeshooterEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(BeeshooterEntity beeshooterEntity) {
			this.beeshooterEntity = beeshooterEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.beeshooterEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.beeshooterEntity.getNavigation().stop();
			this.beeshooterEntity.getLookControl().lookAt(this.beeshooterEntity.getTarget(), 90.0F, 90.0F);
			this.beeshooterEntity.velocityDirty = true;
		}

		public void stop() {
			this.beeshooterEntity.world.sendEntityStatus(this.beeshooterEntity, (byte) 10);
			this.beeshooterEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.beeshooterEntity.getTarget();
			this.beeshooterEntity.getNavigation().stop();
			this.beeshooterEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.beeshooterEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.beeshooterEntity.setTarget((LivingEntity) null);
			} else {
				this.beeshooterEntity.world.sendEntityStatus(this.beeshooterEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.beeshooterEntity.isInsideWaterOrBubbleColumn()) {
						ShootingBeeSpikeEntity proj = new ShootingBeeSpikeEntity(PvZEntity.BEESPIKE, this.beeshooterEntity.world);
						double time = (this.beeshooterEntity.squaredDistanceTo(livingEntity) > 225) ? 50 : 5;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.beeshooterEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.beeshooterEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.beeshooterEntity.getY();
						double g = predictedPos.getZ() - this.beeshooterEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.9F, 0F);
						proj.updatePosition(this.beeshooterEntity.getX(), this.beeshooterEntity.getY() + 0.75D, this.beeshooterEntity.getZ());
						proj.setOwner(this.beeshooterEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.beeshooterEntity.world.sendEntityStatus(this.beeshooterEntity, (byte) 11);
							this.beeshooterEntity.playSound(SoundEvents.ENTITY_BEE_HURT, 0.2F, 1);
							this.beeshooterEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.beeshooterEntity.world.sendEntityStatus(this.beeshooterEntity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
