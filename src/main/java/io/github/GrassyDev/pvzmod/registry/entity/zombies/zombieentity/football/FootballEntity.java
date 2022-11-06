package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.football.HypnoFootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.function.Predicate;

public class FootballEntity extends PvZombieEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";
    private MobEntity owner;
    private int attackTicksLeft;

	public AnimationFactory factory = new AnimationFactory(this);

    public FootballEntity(EntityType<? extends FootballEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
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
		tag.putBoolean("Tackle", this.getTackleStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Tackle"));
	}

	static {

	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(FootballEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum TackleStage {
		TACKLING(true),
		EATING(false);

		TackleStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getTackleStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setTackleStage(FootballEntity.TackleStage tackleStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tackleStage.getId());
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
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            if (!this.getTackleStage()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("football.running", true));
            }
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("football.tackle", true));
            }
        }else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("football.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public FootballEntity(World world) {
        this(PvZEntity.FOOTBALL, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initCustomGoals() {
        this.targetSelector.add(2, new FootballEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(2, new TargetGoal<>(this, PuffshroomEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, PotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, ReinforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, HypnoshroomEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
    }

	public boolean tryAttack(Entity target) {
		int i = this.attackTicksLeft;
		if (!this.hasStatusEffect(PvZCubed.FROZEN)) {
			if (this.getTackleStage()) {
				if (i <= 0) {
					if (this.hasStatusEffect(PvZCubed.ICE)) {
						this.attackTicksLeft = 20;
						this.world.sendEntityStatus(this, (byte) 4);
						float f = 360f;
						boolean bl = target.damage(DamageSource.mob(this), f);
						if (bl) {
							this.applyDamageEffects(this, target);
						}
						this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
						this.setTackleStage(TackleStage.EATING);
						return bl;
					} else {
						this.attackTicksLeft = 20;
						float f = 180f;
						boolean bl = target.damage(DamageSource.mob(this), f);
						if (bl) {
							this.applyDamageEffects(this, target);
						}
						this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
						this.setTackleStage(TackleStage.EATING);
						return bl;
					}
				} else {
					return false;
				}
			} else {
				if (i <= 0) {
					this.attackTicksLeft = 20;
					this.world.sendEntityStatus(this, (byte) 4);
					float f = this.getAttackDamage();
					boolean bl = target.damage(DamageSource.mob(this), f);
					if (bl) {
						this.applyDamageEffects(this, target);
					}
					return bl;
				} else {
					return false;
				}
			}
		}  else {
			return false;
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tickMovement() {
		super.tickMovement();
		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFootballAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.18D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 167D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
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

	public boolean isPushable() {
		return false;
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
            ServerWorld serverWorld = (ServerWorld)this.world;
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                HypnoFootballEntity hypnoFootballEntity = (HypnoFootballEntity) PvZEntity.HYPNOFOOTBALL.create(world);
                hypnoFootballEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoFootballEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoFootballEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoFootballEntity.setAiDisabled(this.isAiDisabled());
				hypnoFootballEntity.setHealth(this.getHealth() + 3);
				if (this.getTackleStage().equals(Boolean.TRUE)){
					hypnoFootballEntity.setTackleStage(HypnoFootballEntity.TackleStage.TACKLING);
				}
				else {
					hypnoFootballEntity.setTackleStage(HypnoFootballEntity.TackleStage.EATING);
				}
                if (this.hasCustomName()) {
                    hypnoFootballEntity.setCustomName(this.getCustomName());
                    hypnoFootballEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoFootballEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoFootballEntity);
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

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return FootballEntity.this.owner != null && FootballEntity.this.owner.getTarget() != null && this.canTrack(FootballEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            FootballEntity.this.setTarget(FootballEntity.this.owner.getTarget());
            super.start();
        }
    }

}
