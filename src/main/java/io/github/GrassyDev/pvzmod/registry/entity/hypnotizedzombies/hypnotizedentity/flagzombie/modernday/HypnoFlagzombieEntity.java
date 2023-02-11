package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoPvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday.HypnoBrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.buckethead.modernday.HypnoBucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday.HypnoConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

public class HypnoFlagzombieEntity extends HypnoSummonerEntity implements IAnimatable {
	private MobEntity owner;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "walkingcontroller";
	private boolean spawning;
	double tonguechance = this.random.nextDouble();

	public HypnoFlagzombieEntity(EntityType<? extends HypnoFlagzombieEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.spawning = true;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public HypnoFlagzombieEntity(World world) {
		this(PvZEntity.HYPNOFLAGZOMBIE, world);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	static {
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(HypnoFlagzombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		FlagZombieVariants variant = Util.getRandom(FlagZombieVariants.values(), this.random);
		setVariant(variant);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FlagZombieVariants getVariant() {
		return FlagZombieVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(FlagZombieVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
			event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.ducky"));
		}else {
			if (tonguechance <= 0.5) {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.walking"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.idle"));
				}
			} else {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.walking2"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.idle2"));
				}
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new HypnoSummonerEntity.AttackGoal());
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new HypnoFlagzombieEntity.summonZombieGoal());
		this.targetSelector.add(2, new HypnoFlagzombieEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster ;
		}));
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHypnoFlagzombieAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	protected SoundEvent getCastSpellSound() {
		return PvZCubed.ENTITYRISINGEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
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

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = HypnoFlagzombieEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (HypnoFlagzombieEntity.this.isSpellcasting()) {
					return false;
				} else {
					return HypnoFlagzombieEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = HypnoFlagzombieEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			HypnoFlagzombieEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = HypnoFlagzombieEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				HypnoFlagzombieEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			HypnoFlagzombieEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				HypnoFlagzombieEntity.this.playSound(HypnoFlagzombieEntity.this.getCastSpellSound(), 1.0F, 1.0F);
			}

		}

		protected abstract void castSpell();

		protected int getInitialCooldown() {
			return 20;
		}

		protected abstract int getSpellTicks();

		protected abstract int startTimeDelay();

		@Nullable
		protected abstract SoundEvent getSoundPrepare();

		protected abstract HypnoSummonerEntity.Spell getSpell();
	}

	class summonZombieGoal extends CastSpellGoal {
		private final TargetPredicate closeZombiePredicate;

		private summonZombieGoal() {
			super();
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

		public boolean canStart() {
			if (HypnoFlagzombieEntity.this.spawning) {
				if (!super.canStart()) {
					return false;
				} else {
					int b = HypnoFlagzombieEntity.this.world.getTargets(HypnoScreendoorEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
					int p = HypnoFlagzombieEntity.this.world.getTargets(HypnoConeheadEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
					int d = HypnoFlagzombieEntity.this.world.getTargets(HypnoBucketheadEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
					int t = HypnoFlagzombieEntity.this.world.getTargets(HypnoBrowncoatEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
					return HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > b &&
							HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > p &&
							HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > d &&
							HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > t;
				}
			} else {
				return false;
			}
		}

		protected int getSpellTicks() {
			return 100;
		}

		protected int startTimeDelay() {
			return 340;
		}

		protected void castSpell() {
			ServerWorld serverWorld = (ServerWorld) HypnoFlagzombieEntity.this.world;

			for (int b = 0; b < 1; ++b) { // 1 hypno screendoor
				BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(-2 + HypnoFlagzombieEntity.this.random.nextInt(10), 1, -2 + HypnoFlagzombieEntity.this.random.nextInt(10));
				HypnoScreendoorEntity hypnoScreendoorEntity = (HypnoScreendoorEntity) PvZEntity.HYPNOSCREENDOOR.create(HypnoFlagzombieEntity.this.world);
				hypnoScreendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				hypnoScreendoorEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				hypnoScreendoorEntity.setOwner(HypnoFlagzombieEntity.this);
				serverWorld.spawnEntityAndPassengers(hypnoScreendoorEntity);
			}
			for (int p = 0; p < 1; ++p) { // 1 hypno conehead
				BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(-2 + HypnoFlagzombieEntity.this.random.nextInt(10), 1, -2 + HypnoFlagzombieEntity.this.random.nextInt(10));
				HypnoConeheadEntity hypnoConeheadEntity = (HypnoConeheadEntity) PvZEntity.HYPNOCONEHEAD.create(HypnoFlagzombieEntity.this.world);
				hypnoConeheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				hypnoConeheadEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				hypnoConeheadEntity.setOwner(HypnoFlagzombieEntity.this);
				serverWorld.spawnEntityAndPassengers(hypnoConeheadEntity);
			}
			for (int d = 0; d < 1; ++d) { // 1 hypno buckethead
				BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(-2 + HypnoFlagzombieEntity.this.random.nextInt(10), 1, -2 + HypnoFlagzombieEntity.this.random.nextInt(10));
				HypnoBucketheadEntity hypnoBucketheadEntity = (HypnoBucketheadEntity) PvZEntity.HYPNOBUCKETHEAD.create(HypnoFlagzombieEntity.this.world);
				hypnoBucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				hypnoBucketheadEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				hypnoBucketheadEntity.setOwner(HypnoFlagzombieEntity.this);
				serverWorld.spawnEntityAndPassengers(hypnoBucketheadEntity);
			}
			for (int t = 0; t < 1; ++t) { // 1 hypno browncoat
				BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(-2 + HypnoFlagzombieEntity.this.random.nextInt(10), 1, -2 + HypnoFlagzombieEntity.this.random.nextInt(10));
				HypnoBrowncoatEntity hypnoBrowncoatEntity = (HypnoBrowncoatEntity) PvZEntity.HYPNOBROWNCOAT.create(HypnoFlagzombieEntity.this.world);
				hypnoBrowncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				hypnoBrowncoatEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				hypnoBrowncoatEntity.setOwner(HypnoFlagzombieEntity.this);
				serverWorld.spawnEntityAndPassengers(hypnoBrowncoatEntity);
				HypnoFlagzombieEntity.this.spawning = false;
			}
		}

		protected SoundEvent getSoundPrepare() {
			return PvZCubed.GRAVERISINGEVENT;
		}

		protected Spell getSpell() {
			return Spell.SUMMON_VEX;
		}
	}

	class TrackOwnerTargetGoal extends TrackTargetGoal {

		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

		public TrackOwnerTargetGoal(PathAwareEntity mob) {
			super(mob, false);
		}

		public boolean canStart() {
			return HypnoFlagzombieEntity.this.owner != null && HypnoFlagzombieEntity.this.owner.getTarget() != null && this.canTrack(HypnoFlagzombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
		}

		public void start() {
			HypnoFlagzombieEntity.this.setTarget(HypnoFlagzombieEntity.this.owner.getTarget());
			super.start();
		}
	}
}
