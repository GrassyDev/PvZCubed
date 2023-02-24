package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.HypnoPvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefaultAndHypnoVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
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

public class DancingZombieEntity extends SummonerEntity implements IAnimatable {
    private MobEntity owner;
    private boolean isAggro;
    private boolean dancing;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "walkingcontroller";
	boolean isFrozen;
	boolean isIced;

    public DancingZombieEntity(EntityType<? extends DancingZombieEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
        this.isAggro = false;
        this.dancing = false;
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
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 13) {
			this.dancing = true;
		}
		else if (status == 12) {
			this.dancing = false;
		}
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
	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(DancingZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.DANCINGZOMBIEHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(DefaultAndHypnoVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DefaultAndHypnoVariants getVariant() {
		return DefaultAndHypnoVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DefaultAndHypnoVariants variant) {
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
		if (this.isInsideWaterOrBubbleColumn()) {
			if (!this.dancing) {
				event.getController().setAnimation(new AnimationBuilder().loop("dancingzombie.ducky"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("dancingzombie.duckydance"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			} else {
				event.getController().setAnimationSpeed(1);
			}
		}
		else {
			if (!this.dancing) {
				event.getController().setAnimation(new AnimationBuilder().loop("dancingzombie.idle"));
			} else {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("dancingzombie.dancewalk"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("dancingzombie.dancing"));
				}
			}
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
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.DANCINGZOMBIEHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new summonZombieGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.targetSelector.add(2, new DancingZombieEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(4, new TargetGoal<>(this, PlantEntity.class, false, true));
		this.targetSelector.add(5, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.getHypno());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) &&
					!(livingEntity instanceof ZombiePropEntity);
		}));
		////////// Must-Protect Plants ///////
		this.targetSelector.add(3, new TargetGoal<>(this, SunflowerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, TwinSunflowerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, SunshroomEntity.class, false, true));
	}

	protected void initHypnoGoals(){
		this.goalSelector.add(1, new summonZombieGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(2, new DancingZombieEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && !(zombiePropEntity.getHypno()));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof ZombiePropEntity);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlayer() != null && !this.CollidesWithPlayer().isCreative()){
				this.setTarget(CollidesWithPlayer());
			}
			else if (this.CollidesWithPlant() != null){
				this.setTarget(CollidesWithPlant());
			}
		}
	}

	protected void mobTick() {
		if (this.isAggro) {
			this.world.sendEntityStatus(this, (byte) 13);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 12);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 72);
		}
		super.mobTick();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DANCINGZOMBIEEGG.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createDancingZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
    }

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
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

	protected SoundEvent getCastSpellSound() {
		return PvZCubed.ENTITYRISINGEVENT;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) {
			return false;
		} else if (!(this.world instanceof ServerWorld)) {
			return false;
		} else {
			ServerWorld serverWorld = (ServerWorld)this.world;
			LivingEntity livingEntity = this.getTarget();
			if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
				livingEntity = (LivingEntity)source.getAttacker();
			}

			if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE && !(this.getHypno())) {
				this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
				DancingZombieEntity hypnotizedZombie = (DancingZombieEntity) PvZEntity.DANCINGZOMBIEHYPNO.create(world);
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}
				for (Entity entity1 : this.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(hypnotizedZombie);
					}
				}

				hypnotizedZombie.setPersistent();
				serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
				this.remove(RemovalReason.DISCARDED);
			}

			if (source.getAttacker() != null) {
				this.isAggro = true;
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

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = DancingZombieEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (DancingZombieEntity.this.isSpellcasting()) {
					return false;
				} else {
					return DancingZombieEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = DancingZombieEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			DancingZombieEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = DancingZombieEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				DancingZombieEntity.this.playSound(soundEvent, 0.5F, 1.0F);
			}

			DancingZombieEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				DancingZombieEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				DancingZombieEntity.this.playSound(DancingZombieEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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

		protected abstract SummonerEntity.Spell getSpell();
	}

    class summonZombieGoal extends DancingZombieEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;
		private final DancingZombieEntity dancingZombieEntity;

        private summonZombieGoal(DancingZombieEntity dancingZombieEntity) {
            super();
			this.dancingZombieEntity = dancingZombieEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (DancingZombieEntity.this.isAggro) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return DancingZombieEntity.this.random.nextInt(8) + 1 > b &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > p &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > d &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > t ;
                }
            } else {
                return false;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 160;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)DancingZombieEntity.this.world;

			EntityType<?> backup = PvZEntity.BACKUPDANCER;
			if (this.dancingZombieEntity.getHypno()){
				backup = PvZEntity.BACKUPDANCERHYPNO;
			}

            for(int b = 0; b < 1; ++b) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(-1, 1, 0);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity) backup.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound) null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
				if (this.dancingZombieEntity.getHypno()){
					backupDancerEntity.createProp();
				}
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int p = 0; p < 1; ++p) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(0, 1, +1);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)backup.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
				if (this.dancingZombieEntity.getHypno()){
					backupDancerEntity.createProp();
				}
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int d = 0; d < 1; ++d) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(+1, 1, 0);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)backup.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
				if (this.dancingZombieEntity.getHypno()){
					backupDancerEntity.createProp();
				}
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int t = 0; t < 1; ++t) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(0, 1, -1);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)backup.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
				if (this.dancingZombieEntity.getHypno()){
					backupDancerEntity.createProp();
				}
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.ZOMBIEDANCINGEVENT;
        }

        protected DancingZombieEntity.Spell getSpell() {
            return DancingZombieEntity.Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return DancingZombieEntity.this.owner != null && DancingZombieEntity.this.owner.getTarget() != null && this.canTrack(DancingZombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            DancingZombieEntity.this.setTarget(DancingZombieEntity.this.owner.getTarget());
            super.start();
        }
    }
}
