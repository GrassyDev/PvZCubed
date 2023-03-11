package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut.CoconutEntity;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

public class CoconutCannonEntity extends PlantEntity implements IAnimatable, RangedAttackMob {
    private String controllerName = "cococontroller";

	public boolean isFiring;
	public boolean blink;
	public int rechargeTime;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private boolean recharged;

	public CoconutCannonEntity(EntityType<? extends CoconutCannonEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
			super.handleStatus(status);
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 88){
			this.recharged = true;
		}
		else if (status == 87) {
			this.recharged = false;
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
			event.getController().setAnimation(new AnimationBuilder().playOnce("coconutcannon.fire"));
		}
		else if (!this.recharged){
			event.getController().setAnimation(new AnimationBuilder().loop("coconutcannon.recharge"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("coconutcannon.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		customGoals();
	}

	protected void customGoals(){
		this.goalSelector.add(1, new CoconutCannonEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(13, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		snorkelGoal();
	}

	protected void snorkelGoal() {
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel() && !(snorkelEntity.getHypno());
		}));
	}

	protected void flyingGoal() {
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.isFlying() && !(generalPvZombieEntity.getHypno());
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
			super.setPosition((double)MathHelper.floor(x), (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z));
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockPos blockPos3 = this.getBlockPos().add(-0.5, 0, 0);
			BlockPos blockPos4 = this.getBlockPos().add(0, 0, -0.5);
			BlockPos blockPos5 = this.getBlockPos().add(0.5, 0, 0);
			BlockPos blockPos6 = this.getBlockPos().add(-0.5, 0, -0.5);
			BlockState blockState = this.getLandingBlockState();
			BlockState blockState3 = this.world.getBlockState(this.getSteppingPosition().add(-0.5, 0, 0));
			BlockState blockState4 = this.world.getBlockState(this.getSteppingPosition().add(0, 0, -0.5));
			BlockState blockState5 = this.world.getBlockState(this.getSteppingPosition().add(0.5, 0, 0));
			BlockState blockState6 = this.world.getBlockState(this.getSteppingPosition().add(-0.5, 0, -0.5));
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this) ||
					!blockState3.hasSolidTopSurface(world, blockPos3, this) ||
					!blockState4.hasSolidTopSurface(world, blockPos4, this) ||
					!blockState6.hasSolidTopSurface(world, blockPos4, this) ||
					!blockState5.hasSolidTopSurface(world, blockPos5, this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.COCONUTCANNON_SEED_PACKET);
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
		--rechargeTime;
		if (rechargeTime <= 0){
			this.world.sendEntityStatus(this, (byte) 88);
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
		return ModItems.COCONUTCANNON_SEED_PACKET.getDefaultStack();
	}

	protected boolean startShooting;

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!this.world.isClient) {
			if (rechargeTime <= 0 && !this.isFiring && this.getTarget() != null) {
				startShooting = true;
				return ActionResult.SUCCESS;
			} else {
				return ActionResult.PASS;
			}
		}
		return ActionResult.PASS;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createCoconutCannonAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0D)
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

	private boolean attacked;


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final CoconutCannonEntity coconutCannonEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(CoconutCannonEntity coconutCannonEntity) {
			this.coconutCannonEntity = coconutCannonEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.coconutCannonEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -9;
			this.animationTicks = -26;
			this.coconutCannonEntity.getNavigation().stop();
			if (this.coconutCannonEntity.startShooting) {
				this.coconutCannonEntity.getLookControl().lookAt(this.coconutCannonEntity.getTarget(), 90.0F, 90.0F);
			}
			this.coconutCannonEntity.velocityDirty = true;
		}

		public void stop() {
			this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 110);
			this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 87);
			if (this.coconutCannonEntity.rechargeTime <= 0 && this.coconutCannonEntity.attacked) {
				this.coconutCannonEntity.attacked = false;
				this.coconutCannonEntity.rechargeTime = 300;
				this.coconutCannonEntity.startShooting = false;
			}
			this.coconutCannonEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.coconutCannonEntity.getTarget();
			this.coconutCannonEntity.getNavigation().stop();
			if (this.coconutCannonEntity.startShooting) {
				this.coconutCannonEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			if (((!this.coconutCannonEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0)) {
				this.coconutCannonEntity.setTarget((LivingEntity) null);
			} else if (this.coconutCannonEntity.startShooting) {
				this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 111);
				if (this.coconutCannonEntity.rechargeTime <= 0){
					++this.beamTicks;
					++this.animationTicks;
				}
				if (this.beamTicks >= 0 && this.coconutCannonEntity.rechargeTime <= 0) {
					if (!this.coconutCannonEntity.isInsideWaterOrBubbleColumn()) {
						CoconutEntity proj = new CoconutEntity(PvZEntity.COCONUTPROJ, this.coconutCannonEntity.world);
						double time = (this.coconutCannonEntity.squaredDistanceTo(livingEntity) > 36) ? 75 : 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.coconutCannonEntity.squaredDistanceTo(predictedPos);
						float df = (float) d;
						double e = predictedPos.getX() - this.coconutCannonEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.coconutCannonEntity.getY();
						double g = predictedPos.getZ() - this.coconutCannonEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.66F, 0F);
						proj.updatePosition(this.coconutCannonEntity.getX(), this.coconutCannonEntity.getY() + 0.75D, this.coconutCannonEntity.getZ());
						proj.setOwner(this.coconutCannonEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -30;
							this.coconutCannonEntity.attacked = true;
							this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 111);
							this.coconutCannonEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.2F, 1);
							this.coconutCannonEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0 && this.coconutCannonEntity.rechargeTime <= 0){
					this.coconutCannonEntity.attacked = false;
					this.coconutCannonEntity.rechargeTime = 300;
				}
				else if (this.coconutCannonEntity.rechargeTime > 0) {
					this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 110);
					this.coconutCannonEntity.world.sendEntityStatus(this.coconutCannonEntity, (byte) 87);
					this.beamTicks = -9;
					this.animationTicks = -26;
					this.coconutCannonEntity.startShooting = false;
				}
				super.tick();
			}
		}
	}
}
