package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.polevaulting;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoPvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
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

public class HypnoPoleVaultingEntity extends HypnoZombieEntity implements IAnimatable {
	private MobEntity owner;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "runningcontroller";

	public HypnoPoleVaultingEntity(EntityType<? extends HypnoPoleVaultingEntity> entityType, World world) {
		super(entityType, world);
		//this.moveControl = new HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl(this);
		this.ignoreCameraFrustum = true;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	static {

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Pole", this.getPoleStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Pole"));
	}

	static {
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(HypnoPoleVaultingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum PoleStage {
		POLE(true),
		NOPOLE	(false);

		PoleStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPoleStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setPoleStage(HypnoPoleVaultingEntity.PoleStage poleStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, poleStage.getId());
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
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof DuckyTubeEntity) {
			event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.ducky"));
		}else {
			if (this.getPoleStage()) {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.running"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.idle"));
				}
			} else {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.running2"));
					event.getController().setAnimationSpeed(0.5F);
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.idle2"));
					event.getController().setAnimationSpeed(1F);
				}
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		/**this.goalSelector.add(2, new HypnoPoleVaultingEntity.FaceTowardTargetGoal(this));
		this.goalSelector.add(1, new HypnoPoleVaultingEntity.SwimmingGoal(this));
		this.goalSelector.add(3, new HypnoPoleVaultingEntity.RandomLookGoal(this));
		this.goalSelector.add(5, new HypnoPoleVaultingEntity.MoveGoal(this));**/
		this.goalSelector.add(1, new HypnoZombieEntity.AttackGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(2, new HypnoPoleVaultingEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof ZombiePropEntity);
		}));
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHypnoPoleVaultingAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	float getJumpSoundPitch() {
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	protected SoundEvent getJumpSound() {
		return PvZCubed.POLEVAULTEVENT;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	private static class HypnoPoleVaultingMoveControl extends MoveControl {
		private float targetYaw;
		private int ticksUntilJump;
		private final HypnoPoleVaultingEntity hypnoPoleVaulting;
		private boolean jumpOften;

		public HypnoPoleVaultingMoveControl(HypnoPoleVaultingEntity hypnoPoleVaulting) {
			super(hypnoPoleVaulting);
			this.hypnoPoleVaulting = hypnoPoleVaulting;
			this.targetYaw = 180.0F * hypnoPoleVaulting.getYaw() / 3.1415927F;
		}

		public void look(float targetYaw, boolean jumpOften) {
			this.targetYaw = targetYaw;
			this.jumpOften = jumpOften;
		}

		public void move(double speed) {
			this.speed = speed;
			this.state = State.MOVE_TO;
		}

		public void tick() {
			this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
			this.entity.headYaw = this.entity.getYaw();
			this.entity.bodyYaw = this.entity.getYaw();
			LivingEntity livingEntity = this.hypnoPoleVaulting.getTarget();
			if (livingEntity != null) {
				if (this.hypnoPoleVaulting.squaredDistanceTo(this.hypnoPoleVaulting.getTarget()) <= 1) {
					this.state = State.WAIT;
				}
			}
			if (this.state == State.WAIT){
				this.entity.setMovementSpeed(0);
				this.hypnoPoleVaulting.upwardSpeed = 0f;
			}
			else if (this.state != State.MOVE_TO) {
				this.entity.setForwardSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			} else {
				if (this.entity.isOnGround() && this.hypnoPoleVaulting.getPoleStage()) {
					if (this.hypnoPoleVaulting.getTarget() != null) {
						if (this.hypnoPoleVaulting.squaredDistanceTo(this.hypnoPoleVaulting.getTarget()) < 49) {
							this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
							this.hypnoPoleVaulting.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 4.5));
							this.hypnoPoleVaulting.upwardSpeed = 0.35f;
							this.hypnoPoleVaulting.getJumpControl().setActive();
							this.hypnoPoleVaulting.playSound(this.hypnoPoleVaulting.getJumpSound(), this.hypnoPoleVaulting.getSoundVolume(), this.hypnoPoleVaulting.getJumpSoundPitch());
							this.hypnoPoleVaulting.setPoleStage(HypnoPoleVaultingEntity.PoleStage.NOPOLE);
						} else {
							this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
						}
					}
				}
				else if (this.hypnoPoleVaulting.isOnGround()) {
					this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / 1.4));
				}
			}
		}
	}

	static class RandomLookGoal extends Goal {
		private final HypnoPoleVaultingEntity poleVaultingEntity;
		private float targetYaw;
		private int timer;

		public RandomLookGoal(HypnoPoleVaultingEntity poleVaultingEntity) {
			this.poleVaultingEntity = poleVaultingEntity;
			this.setControls(EnumSet.of(Control.LOOK));
		}

		public boolean canStart() {
			return this.poleVaultingEntity.getTarget() == null && (this.poleVaultingEntity.onGround || this.poleVaultingEntity.isTouchingWater() || this.poleVaultingEntity.isInLava() || this.poleVaultingEntity.hasStatusEffect(StatusEffects.LEVITATION)) && this.poleVaultingEntity.getMoveControl() instanceof HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl;
		}

		public void tick() {
			if (this.poleVaultingEntity.getTarget() == null) {
				if (--this.timer <= 0) {
					this.timer = this.getTickCount(40 + this.poleVaultingEntity.getRandom().nextInt(60));
					this.targetYaw = (float) this.poleVaultingEntity.getRandom().nextInt(360);
				}
				((HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl) this.poleVaultingEntity.getMoveControl()).look(this.targetYaw, false);
			}
		}
	}

	static class SwimmingGoal extends Goal {
		private final HypnoPoleVaultingEntity poleVaultingEntity;

		public SwimmingGoal(HypnoPoleVaultingEntity poleVaultingEntity) {
			this.poleVaultingEntity = poleVaultingEntity;
			this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
			poleVaultingEntity.getNavigation().setCanSwim(true);
		}

		public boolean canStart() {
			return (this.poleVaultingEntity.isTouchingWater() || this.poleVaultingEntity.isInLava()) && this.poleVaultingEntity.getMoveControl() instanceof HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			if (this.poleVaultingEntity.getRandom().nextFloat() < 0.8F) {
				this.poleVaultingEntity.getJumpControl().setActive();
			}

			((HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl) this.poleVaultingEntity.getMoveControl()).move(2);
		}
	}



	static class FaceTowardTargetGoal extends Goal {
		private final HypnoPoleVaultingEntity hypnoPoleVaulting;
		private int ticksLeft;

		public FaceTowardTargetGoal(HypnoPoleVaultingEntity hypnoPoleVaulting) {
			this.hypnoPoleVaulting = hypnoPoleVaulting;
			this.setControls(EnumSet.of(Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.hypnoPoleVaulting.getTarget();
			if (livingEntity == null) {
				return false;
			} else {
				return this.hypnoPoleVaulting.canTarget(livingEntity) && this.hypnoPoleVaulting.getMoveControl() instanceof HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl;
			}
		}

		public void start() {
			this.ticksLeft = toGoalTicks(300);
			super.start();
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = this.hypnoPoleVaulting.getTarget();
			if (livingEntity == null) {
				return false;
			} else if (!this.hypnoPoleVaulting.canTarget(livingEntity)) {
				return false;
			} else {
				return --this.ticksLeft > 0;
			}
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingEntity = this.hypnoPoleVaulting.getTarget();
			if (livingEntity != null) {
				this.hypnoPoleVaulting.lookAtEntity(livingEntity, 360.0F, 360.0F);
			}

			((HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl)this.hypnoPoleVaulting.getMoveControl()).look(this.hypnoPoleVaulting.getYaw(), true);
		}
	}

	static class MoveGoal extends Goal {
		private final HypnoPoleVaultingEntity hypnoPoleVaulting;

		public MoveGoal(HypnoPoleVaultingEntity hypnoPoleVaulting) {
			this.hypnoPoleVaulting = hypnoPoleVaulting;
			this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
		}

		public boolean canStart() {
			return !this.hypnoPoleVaulting.hasVehicle();
		}

		public void tick() {
			((HypnoPoleVaultingEntity.HypnoPoleVaultingMoveControl)this.hypnoPoleVaulting.getMoveControl()).move(1.0);
		}
	}

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

		public TrackOwnerTargetGoal(PathAwareEntity mob) {
			super(mob, false);
		}

		public boolean canStart() {
			return HypnoPoleVaultingEntity.this.owner != null && HypnoPoleVaultingEntity.this.owner.getTarget() != null && this.canTrack(HypnoPoleVaultingEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
		}

		public void start() {
			HypnoPoleVaultingEntity.this.setTarget(HypnoPoleVaultingEntity.this.owner.getTarget());
			super.start();
		}
	}
}
