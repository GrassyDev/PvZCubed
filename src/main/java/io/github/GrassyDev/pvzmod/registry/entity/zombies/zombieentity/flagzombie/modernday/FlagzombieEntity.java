package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FlagzombieEntity extends SummonerEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";
    private MobEntity owner;
    private boolean isAggro;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    double tonguechance = this.random.nextDouble();

    public FlagzombieEntity(EntityType<? extends FlagzombieEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
        this.isAggro = false;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
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
			DataTracker.registerData(FlagzombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

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

	private void setVariant(FlagZombieVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data)
	{
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (tonguechance <= 0.5) {
            if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
                event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.walking"));
				event.getController().setAnimationSpeed(1.66);
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.idle"));
				event.getController().setAnimationSpeed(1);
            }
        }
        else {
            if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
                event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.walking2"));
				event.getController().setAnimationSpeed(1.66);
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.idle2"));
				event.getController().setAnimationSpeed(1);
            }
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new FlagzombieEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new FlagzombieEntity.summonZombieGoal());
        this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof ReinforceEntity && !(livingEntity instanceof LilyPadEntity);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, LilyPadEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFlagzombieZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
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

	protected SoundEvent getHurtSound() {
		return PvZCubed.SILENCEVENET;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
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

			if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
				this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
				HypnoFlagzombieEntity hypnoFlagzombieEntity = (HypnoFlagzombieEntity)PvZEntity.HYPNOFLAGZOMBIE.create(world);
				hypnoFlagzombieEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnoFlagzombieEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoFlagzombieEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				hypnoFlagzombieEntity.setAiDisabled(this.isAiDisabled());
				hypnoFlagzombieEntity.setHealth(this.getHealth() + 3);
				hypnoFlagzombieEntity.setVariant(this.getVariant());
				if (this.hasCustomName()) {
					hypnoFlagzombieEntity.setCustomName(this.getCustomName());
					hypnoFlagzombieEntity.setCustomNameVisible(this.isCustomNameVisible());
				}

				hypnoFlagzombieEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(hypnoFlagzombieEntity);
				this.remove(RemovalReason.DISCARDED);
			}

			if (source.getAttacker() instanceof LivingEntity) {
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
			LivingEntity livingEntity = FlagzombieEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (FlagzombieEntity.this.isSpellcasting()) {
					return false;
				} else {
					return FlagzombieEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = FlagzombieEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			FlagzombieEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = FlagzombieEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				FlagzombieEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			FlagzombieEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				FlagzombieEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				FlagzombieEntity.this.playSound(FlagzombieEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

        private summonZombieGoal() {
            super();
            this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
        }

        public boolean canStart() {
            if (FlagzombieEntity.this.isAggro) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = FlagzombieEntity.this.world.getTargets(ScreendoorEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = FlagzombieEntity.this.world.getTargets(ConeheadEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = FlagzombieEntity.this.world.getTargets(BucketheadEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = FlagzombieEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return FlagzombieEntity.this.random.nextInt(8) + 1 > b &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > p &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > d &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > t ;
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
            ServerWorld serverWorld = (ServerWorld) FlagzombieEntity.this.world;

            for(int b = 0; b < 1; ++b) { // 1 Screendoor
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 0.1, -2 + FlagzombieEntity.this.random.nextInt(10));
                ScreendoorEntity screendoorEntity = (ScreendoorEntity) PvZEntity.SCREEENDOOR.create(FlagzombieEntity.this.world);
                screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                screendoorEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound) null);
                screendoorEntity.setOwner(FlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(screendoorEntity);
            }
            for(int p = 0; p < 1; ++p) { // 1 Conehead
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 0.1, -2 + FlagzombieEntity.this.random.nextInt(10));
                ConeheadEntity coneheadEntity = (ConeheadEntity)PvZEntity.CONEHEAD.create(FlagzombieEntity.this.world);
                coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                coneheadEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                coneheadEntity.setOwner(FlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(coneheadEntity);
            }
            for(int d = 0; d < 1; ++d) { // 1 Buckethead
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 0.1, -2 + FlagzombieEntity.this.random.nextInt(10));
                BucketheadEntity bucketheadEntity = (BucketheadEntity)PvZEntity.BUCKETHEAD.create(FlagzombieEntity.this.world);
                bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                bucketheadEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                bucketheadEntity.setOwner(FlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(bucketheadEntity);
            }
            for(int t = 0; t < 1; ++t) { // 1 Browncoat
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 0.1, -2 + FlagzombieEntity.this.random.nextInt(10));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(FlagzombieEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(FlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
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
            return FlagzombieEntity.this.owner != null && FlagzombieEntity.this.owner.getTarget() != null && this.canTrack(FlagzombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            FlagzombieEntity.this.setTarget(FlagzombieEntity.this.owner.getTarget());
            super.start();
        }
    }
}
