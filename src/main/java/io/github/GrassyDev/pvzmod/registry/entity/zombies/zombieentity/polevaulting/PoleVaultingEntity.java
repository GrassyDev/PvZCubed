package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.polevaulting.HypnoPoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PoleVaultingEntity extends PvZombieEntity implements IAnimatable {

	private MobEntity owner;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public boolean firstAttack;
	private String controllerName = "runningcontroller";
	boolean isFrozen;
	boolean isIced;

	public PoleVaultingEntity(EntityType<? extends PoleVaultingEntity> entityType, World world) {
		super(entityType, world);
		//this.moveControl = new PoleVaultingEntity.PoleVaultingMoveControl(this);
		this.ignoreCameraFrustum = true;
		this.experiencePoints = 3;
		this.firstAttack = true;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
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

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 70) {
			this.isFrozen = true;
			this.isIced = false;
		}
		else if (status == 71) {
			this.isIced = true;
			this.isFrozen = false;
		}
		else if (status == 72) {
			this.isIced = false;
			this.isFrozen = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(PoleVaultingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


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

	private void setPoleStage(PoleVaultingEntity.PoleStage poleStage) {
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
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (this.getPoleStage()) {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.running"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.idle"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				}
			} else {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.running2"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("polevaulting.idle2"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				}
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		/**this.goalSelector.add(1, new PoleVaultingEntity.FaceTowardTargetGoal(this));
		this.goalSelector.add(1, new PoleVaultingEntity.SwimmingGoal(this));
		this.goalSelector.add(3, new PoleVaultingEntity.RandomLookGoal(this));
		this.goalSelector.add(5, new PoleVaultingEntity.MoveGoal(this));**/
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(2, new PoleVaultingEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(4, new TargetGoal<>(this, PotatomineEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, ReinforceEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, SpearEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(4, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.updateFloating();
		if (this.getAttacking() == null){
			if (this.CollidesWithPlayer() != null && !this.CollidesWithPlayer().isCreative()){
				this.setTarget(CollidesWithPlayer());
			}
			else if (this.CollidesWithPlant() != null) {
				if (!this.isInsideWaterOrBubbleColumn()){
					if (!((this.CollidesWithPlant() instanceof ReinforceEntity reinforceEntity && !(reinforceEntity instanceof LilyPadEntity)) ||
							this.CollidesWithPlant() instanceof EnforceEntity)){
						this.setTarget(CollidesWithPlant());
					}
				}
				else {
					this.setTarget(CollidesWithPlant());
				}
			}
		}
	}

	protected void mobTick() {
		super.mobTick();
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 72);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	private void updateFloating() {
		if (this.isInsideWaterOrBubbleColumn()) {
			ShapeContext shapeContext = ShapeContext.of(this);
			if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.world.getFluidState(this.getBlockPos().up()).isIn(FluidTags.WATER)) {
				this.onGround = true;
			}
		}
	}

	public static DefaultAttributeContainer.Builder createPoleVaultingAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
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


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) {
			return false;
		} else if (!(this.world instanceof ServerWorld)) {
			return false;
		} else {
			ServerWorld serverWorld = (ServerWorld) this.world;
			LivingEntity livingEntity = this.getTarget();
			if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
				livingEntity = (LivingEntity) source.getAttacker();
			}

			if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
				this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
				HypnoPoleVaultingEntity hypnotizedZombie = (HypnoPoleVaultingEntity) PvZEntity.HYPNOPOLEVAULTING.create(world);
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.getPoleStage().equals(Boolean.TRUE)){
					hypnotizedZombie.setPoleStage(HypnoPoleVaultingEntity.PoleStage.POLE);
				}
				else {
					hypnotizedZombie.setPoleStage(HypnoPoleVaultingEntity.PoleStage.NOPOLE);
				}
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}

				hypnotizedZombie.setPersistent();
				serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
				this.remove(RemovalReason.DISCARDED);
			}

			return true;
		}
	}

	public boolean onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		boolean bl = super.onKilledOther(serverWorld, livingEntity);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			VillagerEntity villagerEntity = (VillagerEntity) livingEntity;
			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			zombieVillagerEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound) null);
			zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
			zombieVillagerEntity.setGossipData((NbtElement) villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
			zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
			zombieVillagerEntity.setXp(villagerEntity.getExperience());
			if (!this.isSilent()) {
				serverWorld.syncWorldEvent((PlayerEntity) null, 1026, this.getBlockPos(), 0);
			}
		}

		return bl;
	}


	/** /~*~//~*GOALS*~//~*~/ **/
/**
	private static class PoleVaultingMoveControl extends MoveControl {
		private float targetYaw;
		private int ticksUntilJump;
		private final PoleVaultingEntity poleVaulting;
		private boolean jumpOften;

		public PoleVaultingMoveControl(PoleVaultingEntity poleVaulting) {
			super(poleVaulting);
			this.poleVaulting = poleVaulting;
			this.targetYaw = 180.0F * poleVaulting.getYaw() / 3.1415927F;
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
			LivingEntity livingEntity = this.poleVaulting.getTarget();
			if (livingEntity != null) {
				if (this.poleVaulting.squaredDistanceTo(this.poleVaulting.getTarget()) <= 1) {
					this.state = State.WAIT;
				}
			}
			if (this.state == State.WAIT){
				this.entity.setMovementSpeed(0);
				this.poleVaulting.upwardSpeed = 0f;
			}
			else if (this.state != State.MOVE_TO) {
				this.entity.setForwardSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			} else {
				if (this.entity.isOnGround() && this.poleVaulting.getPoleStage()) {
					if (this.poleVaulting.getTarget() != null) {
						if (this.poleVaulting.squaredDistanceTo(this.poleVaulting.getTarget()) < 49) {
							this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
								this.poleVaulting.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 4.5));
								this.poleVaulting.upwardSpeed = 0.35f;
								this.poleVaulting.getJumpControl().setActive();
								this.poleVaulting.playSound(this.poleVaulting.getJumpSound(), this.poleVaulting.getSoundVolume(), this.poleVaulting.getJumpSoundPitch());
								this.poleVaulting.setPoleStage(PoleStage.NOPOLE);
						} else {
							this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
						}
					}
				}
				else if (this.poleVaulting.isOnGround()) {
					this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / 1.4));
				}
			}
		}
	}

	static class RandomLookGoal extends Goal {
		private final PoleVaultingEntity poleVaultingEntity;
		private float targetYaw;
		private int timer;

		public RandomLookGoal(PoleVaultingEntity poleVaultingEntity) {
			this.poleVaultingEntity = poleVaultingEntity;
			this.setControls(EnumSet.of(Control.LOOK));
		}

		public boolean canStart() {
			return this.poleVaultingEntity.getTarget() == null && (this.poleVaultingEntity.onGround || this.poleVaultingEntity.isTouchingWater() || this.poleVaultingEntity.isInLava() || this.poleVaultingEntity.hasStatusEffect(StatusEffects.LEVITATION)) && this.poleVaultingEntity.getMoveControl() instanceof PoleVaultingEntity.PoleVaultingMoveControl;
		}

		public void tick() {
			if (this.poleVaultingEntity.getTarget() == null) {
				if (--this.timer <= 0) {
					this.timer = this.getTickCount(40 + this.poleVaultingEntity.getRandom().nextInt(60));
					this.targetYaw = (float) this.poleVaultingEntity.getRandom().nextInt(360);
				}

				if (this.poleVaultingEntity.getMoveControl() instanceof PoleVaultingMoveControl) {
					((PoleVaultingEntity.PoleVaultingMoveControl) this.poleVaultingEntity.getMoveControl()).look(this.targetYaw, false);
				}
			}
		}
	}

	static class SwimmingGoal extends Goal {
		private final PoleVaultingEntity poleVaultingEntity;

		public SwimmingGoal(PoleVaultingEntity poleVaultingEntity) {
			this.poleVaultingEntity = poleVaultingEntity;
			this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
			poleVaultingEntity.getNavigation().setCanSwim(true);
		}

		public boolean canStart() {
			return (this.poleVaultingEntity.isTouchingWater() || this.poleVaultingEntity.isInLava()) && this.poleVaultingEntity.getMoveControl() instanceof PoleVaultingEntity.PoleVaultingMoveControl;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			if (this.poleVaultingEntity.getRandom().nextFloat() < 0.8F) {
				this.poleVaultingEntity.getJumpControl().setActive();
			}

			if (this.poleVaultingEntity.getMoveControl() instanceof PoleVaultingMoveControl) {
				((PoleVaultingEntity.PoleVaultingMoveControl) this.poleVaultingEntity.getMoveControl()).move(2);
			}
		}
	}

	static class FaceTowardTargetGoal extends Goal {
		private final PoleVaultingEntity poleVaulting;

		public FaceTowardTargetGoal(PoleVaultingEntity poleVaulting) {
			this.poleVaulting = poleVaulting;
			this.setControls(EnumSet.of(Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.poleVaulting.getTarget();
			if (livingEntity == null) {
				return false;
			} else {
				return this.poleVaulting.canTarget(livingEntity) && this.poleVaulting.getMoveControl() instanceof PoleVaultingMoveControl;
			}
		}

		public void start() {
			super.start();
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = this.poleVaulting.getTarget();
			if (livingEntity == null) {
				return false;
			} else if (!this.poleVaulting.canTarget(livingEntity)) {
				return false;
			} else {
				return true;
			}
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingEntity = this.poleVaulting.getTarget();
			if (livingEntity != null) {
				this.poleVaulting.lookAtEntity(livingEntity, 360.0F, 360.0F);
			}

			if (this.poleVaulting.getMoveControl() instanceof PoleVaultingMoveControl){
				((PoleVaultingEntity.PoleVaultingMoveControl)this.poleVaulting.getMoveControl()).look(this.poleVaulting.getYaw(), true);
			}
		}
	}

	static class MoveGoal extends Goal {
		private final PoleVaultingEntity poleVaulting;

		public MoveGoal(PoleVaultingEntity poleVaulting) {
			this.poleVaulting = poleVaulting;
			this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
		}

		public boolean canStart() {
			return !this.poleVaulting.hasVehicle();
		}

		public void tick() {

			if (this.poleVaulting.getMoveControl() instanceof PoleVaultingMoveControl) {
				((PoleVaultingEntity.PoleVaultingMoveControl) this.poleVaulting.getMoveControl()).move(1.0);
			}
		}
	}
 **/

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

		public TrackOwnerTargetGoal(PathAwareEntity mob) {
			super(mob, false);
		}

		public boolean canStart() {
			return PoleVaultingEntity.this.owner != null && PoleVaultingEntity.this.owner.getTarget() != null && this.canTrack(PoleVaultingEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
		}

		public void start() {
			PoleVaultingEntity.this.setTarget(PoleVaultingEntity.this.owner.getTarget());
			super.start();
		}
	}
}
