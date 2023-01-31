package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.GatlingpeaSeeds;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

public class RepeaterEntity extends AppeaseEntity implements RangedAttackMob, IAnimatable {
	private String controllerName = "peacontroller";

	public int healingTime;

	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public RepeaterEntity(EntityType<? extends RepeaterEntity> entityType, World world) {
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
			event.getController().setAnimation(new AnimationBuilder().playOnce("peashooter.shoot2"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("peashooter.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new RepeaterEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(40) + 35, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
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


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.GATLINGPEA_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				GatlingpeaEntity gatlingpeaEntity = (GatlingpeaEntity) PvZEntity.GATLINGPEA.create(world);
				gatlingpeaEntity.setTarget(this.getTarget());
				gatlingpeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				gatlingpeaEntity.initialize(serverWorld, world.getLocalDifficulty(gatlingpeaEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
				gatlingpeaEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					gatlingpeaEntity.setCustomName(this.getCustomName());
					gatlingpeaEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					gatlingpeaEntity.startRiding(this.getVehicle(), true);
				}

				gatlingpeaEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(gatlingpeaEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.GATLINGPEA_SEED_PACKET, GatlingpeaSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.CONSUME;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createRepeaterAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
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
						double time = 50;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.repeaterEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.repeaterEntity.getX();
						double f = livingEntity.getY() - this.repeaterEntity.getY();
						double g = predictedPos.getZ() - this.repeaterEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
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
