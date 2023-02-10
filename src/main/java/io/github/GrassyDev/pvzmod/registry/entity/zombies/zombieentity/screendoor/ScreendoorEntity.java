package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
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

public class ScreendoorEntity extends PvZombieEntity implements IAnimatable {
    private MobEntity owner;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";
	boolean isFrozen;
	boolean isIced;

    public ScreendoorEntity(EntityType<? extends ScreendoorEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public ScreendoorEntity(World world) {
		this(PvZEntity.SCREEENDOOR, world);
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
			event.getController().setAnimation(new AnimationBuilder().loop("screendoor.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(new AnimationBuilder().loop("screendoor.walking"));
				if (this.isFrozen) {
					event.getController().setAnimationSpeed(0);
				}
				else if (this.isIced) {
					event.getController().setAnimationSpeed(0.83);
				}
				else {
					event.getController().setAnimationSpeed(1.66);
				}
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("screendoor.idle"));
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
        this.targetSelector.add(2, new ScreendoorEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof ReinforceEntity && !(livingEntity instanceof LilyPadEntity) && !(livingEntity instanceof SmallNutEntity);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false, true));
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


	/** /~*~//~*TICKING*~//~*~/ **/

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

	public static DefaultAttributeContainer.Builder createScreendoorAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180D);
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
                HypnoScreendoorEntity hypnotizedZombie = (HypnoScreendoorEntity)PvZEntity.HYPNOSCREENDOOR.create(world);
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
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

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return ScreendoorEntity.this.owner != null && ScreendoorEntity.this.owner.getTarget() != null && this.canTrack(ScreendoorEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            ScreendoorEntity.this.setTarget(ScreendoorEntity.this.owner.getTarget());
            super.start();
        }
    }

}
