package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GargantuarEntity extends PvZombieEntity implements IAnimatable {
	private String controllerName = "walkingcontroller";

    private MobEntity owner;

	private int animationTicksLeft;
	private int launchAnimation;
    public boolean firstAttack;
    public boolean inAnimation;
	public boolean inLaunchAnimation;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	boolean isFrozen;
	boolean isIced;
	int animationMultiplier = 1;

	public GargantuarEntity(EntityType<? extends GargantuarEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 100;
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
		tag.putBoolean("Imp", this.getImpStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Imp"));
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
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 7) {
			Entity target = this.getTarget();
			if (target != null) {
				for (int i = 0; i < 128; ++i) {
					double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
					this.world.addParticle(ParticleTypes.WATER_SPLASH, target.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
							target.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 3F),
							target.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
							0, e, 0);
				}
			}
		}
		if (status == 13) {
			this.inAnimation = true;
		}
		else if (status == 12) {
			this.inAnimation = false;
		}
		if (status == 44) {
			this.inLaunchAnimation = true;
		}
		else if (status == 43) {
			this.inLaunchAnimation = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(GargantuarEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum ImpStage {
		IMP(true),
		NOIMP(false);

		ImpStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getImpStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setImpStage(ImpStage impStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, impStage.getId());
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
			if (inLaunchAnimation) {
				event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.ducky.throw"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				}
				else {
					event.getController().setAnimationSpeed(1);
				}
			} else if (this.getImpStage()) {
				if (inAnimation) {
					event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.ducky.smash"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.ducky.walk"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.ducky"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				}
			} else {
				if (inAnimation) {
					event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.ducky.smash2"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.ducky.walk2"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.ducky2"));
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				}
			}
		}
		else {
			if (inLaunchAnimation) {
				event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.throw"));
				if (this.isFrozen) {
					event.getController().setAnimationSpeed(0);
				}
				else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				}
				else {
					event.getController().setAnimationSpeed(1);
				}
			} else if (this.getImpStage()) {
				if (inAnimation) {
					event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.smash"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.walk"));
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
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.idle"));
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
				if (inAnimation) {
					event.getController().setAnimation(new AnimationBuilder().playOnce("gargantuar.smash2"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					}
					else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.walk2"));
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
					event.getController().setAnimation(new AnimationBuilder().loop("gargantuar.idle2"));
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
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new GargantuarEntity.TrackOwnerTargetGoal(this));

		this.goalSelector.add(1, new GargantuarEntity.AttackGoal());
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof ReinforceEntity && !(livingEntity instanceof LilyPadEntity) && !(livingEntity instanceof SmallNutEntity);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, SpearEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, LilyPadEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, SmallNutEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
    }

	//Smash
	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.inLaunchAnimation) {
			if (this.firstAttack && this.animationTicksLeft <= 0) {
				this.animationTicksLeft = 90 * animationMultiplier;
				this.firstAttack = false;
			}
			else if (this.animationTicksLeft == 40 * animationMultiplier) {
				if (this.hasStatusEffect(PvZCubed.ICE) && this.squaredDistanceTo(target) < 32D) {
					target.damage(DamageSource.mob(this), 720f);
				} else if (this.squaredDistanceTo(target) < 32D) {
					target.damage(DamageSource.mob(this), 360f);
				}
			}
		}
		return false;
	}

	//Launch Imp
	public void tryLaunch(Entity target){
		if (this.getImpStage().equals(Boolean.TRUE) && launchAnimation == 20 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN)){
			ImpEntity imp = new ImpEntity(PvZEntity.IMP, this.world);
			if (target != null){
				double d = this.squaredDistanceTo(target);
				float df = (float) d;
				double e = target.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = target.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				imp.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.25F, 0F);
			}
			else {
				imp.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 2.25F, 0F);
			}
			imp.updatePosition(this.getX(), this.getY() + 3.95D, this.getZ());
			imp.setOwner(this);
			this.setImpStage(ImpStage.NOIMP);
			this.playSound(PvZCubed.IMPLAUNCHEVENT, 1F, 1);
			this.world.spawnEntity(imp);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void mobTick() {
		super.mobTick();
		Entity vehicle = this.getVehicle();
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.world.sendEntityStatus(this, (byte) 71);
			this.animationMultiplier = 2;
		}
		else {
			this.world.sendEntityStatus(this, (byte) 72);
			this.animationMultiplier = 1;
		}
		if (this.animationTicksLeft <= 0){
			if (this.getHealth() <= 360 && getTarget() != null && this.getImpStage().equals(Boolean.TRUE) && !this.inLaunchAnimation) {
				this.launchAnimation = 50 * animationMultiplier;
				this.inLaunchAnimation = true;
				this.world.sendEntityStatus(this, (byte) 44);
			}
			if (this.launchAnimation > 0) {
				this.getNavigation().stop();
				if (vehicle instanceof DuckyTubeEntity){
					((DuckyTubeEntity) vehicle).getNavigation().stop();
					((DuckyTubeEntity) vehicle).getNavigation().stop();
				}
				--launchAnimation;
				tryLaunch(getTarget());
				this.inLaunchAnimation = true;
				this.world.sendEntityStatus(this, (byte) 44);
			}
			else {
				this.inLaunchAnimation = false;
				this.world.sendEntityStatus(this, (byte) 43);
			}
		}
		if (this.animationTicksLeft == 40 * animationMultiplier && !inLaunchAnimation) {
			if (!this.isInsideWaterOrBubbleColumn() && !this.hasStatusEffect(PvZCubed.FROZEN)) {
				this.playSound(PvZCubed.GARGANTUARSMASHEVENT, 1F, 1.0F);
			}
			else if (!this.hasStatusEffect(PvZCubed.FROZEN)) {
				world.sendEntityStatus(this, (byte) 7);
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
			}
			if (getTarget() != null) {
				this.firstAttack = true;
				tryAttack(getTarget());}
		}
		else if (getTarget() == null){
			this.firstAttack = true;
		}
		if (this.animationTicksLeft > 0) {
			this.getNavigation().stop();
			if (vehicle instanceof DuckyTubeEntity){
				((DuckyTubeEntity) vehicle).getNavigation().stop();
				((DuckyTubeEntity) vehicle).getNavigation().stop();
			}
			--this.animationTicksLeft;
			this.world.sendEntityStatus(this, (byte) 13);
		}
		else{
			this.world.sendEntityStatus(this, (byte) 12);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGargantuarAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 540D);
    }

	protected SoundEvent getAmbientSound() {
		return PvZCubed.GARGANTUARMOANEVENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getHurtSound() {
		return PvZCubed.SILENCEVENET;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return PvZCubed.SILENCEVENET;
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
                HypnoGargantuarEntity hypnotizedZombie = (HypnoGargantuarEntity) PvZEntity.HYPNOGARGANTUAR.create(world);
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.getImpStage().equals(Boolean.TRUE)){
					hypnotizedZombie.setImpStage(HypnoGargantuarEntity.ImpStage.IMP);
				}
				else {
					hypnotizedZombie.setImpStage(HypnoGargantuarEntity.ImpStage.NOIMP);
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


	/** /~*~//~*GOALS*~//~*~/ **/

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return GargantuarEntity.this.owner != null && GargantuarEntity.this.owner.getTarget() != null && this.canTrack(GargantuarEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            GargantuarEntity.this.setTarget(GargantuarEntity.this.owner.getTarget());
            super.start();
        }
    }

	private class AttackGoal extends PvZombieAttackGoal {
		public AttackGoal() {
			super(GargantuarEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = GargantuarEntity.this.getWidth() - 0.1F;
			return (double)(f * 4F * f * 4F + entity.getWidth());
		}
	}

}
