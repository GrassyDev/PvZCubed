package io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BasicGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    public AnimationFactory factory = new AnimationFactory(this);

    public BasicGraveEntity(EntityType<BasicGraveEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 25;
    }

	static {
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
        if (tiltchance <= 0.5) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gravestone.idle", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gravestone.idle2", true));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
        this.targetSelector.add(1, new TargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new BasicGraveEntity.summonZombieGoal(this));
        this.targetSelector.add(2, new BasicGraveEntity.TrackOwnerTargetGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		LocalDifficulty localDifficulty = world.getLocalDifficulty(this.getBlockPos());
		double difficulty = localDifficulty.getLocalDifficulty();
		if (this.spawnCounter == 1 && difficulty <= 1.509){
			this.kill();
		}
		else if (this.spawnCounter == 2 && difficulty <= 1.609){
			this.kill();
		}
		else if (this.spawnCounter == 3 && difficulty <= 1.809){
			this.kill();
		}
		else if (this.spawnCounter == 4 && difficulty <= 2.109){
			this.kill();
		}
		else if (this.spawnCounter == 5 && difficulty >= 2.2){
			this.kill();
		}
		if (this.world.isClient && this.isSpellcasting()) {
			GraveEntity.Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBasicGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 140D);
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canBasicGraveSpawn(EntityType<? extends BasicGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && canMobSpawn(type, world, spawnReason, pos, random);
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = BasicGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (BasicGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return BasicGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = BasicGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			BasicGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = BasicGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				BasicGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			BasicGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				BasicGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				BasicGraveEntity.this.playSound(BasicGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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

		protected abstract GraveEntity.Spell getSpell();
	}

	class summonZombieGoal extends BasicGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final BasicGraveEntity basicGraveEntity;

		private summonZombieGoal(BasicGraveEntity basicGraveEntity) {
            super();
			this.basicGraveEntity = basicGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int b = BasicGraveEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int c = BasicGraveEntity.this.world.getTargets(ConeheadEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int u = BasicGraveEntity.this.world.getTargets(BucketheadEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int p = BasicGraveEntity.this.world.getTargets(PoleVaultingEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int f = BasicGraveEntity.this.world.getTargets(FlagzombieEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
				int g = BasicGraveEntity.this.world.getTargets(GargantuarEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                return BasicGraveEntity.this.random.nextInt(8) + 1 > b &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > c &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > u &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > p &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > f &&
						BasicGraveEntity.this.random.nextInt(8) + 1 > g;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)BasicGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.basicGraveEntity.getBlockPos());
			double difficulty = localDifficulty.getLocalDifficulty();
			double clampedDifficulty = localDifficulty.getClampedLocalDifficulty();
			int unlockedZombie = 0;
			if (clampedDifficulty > 0){
				unlockedZombie = 1;
			}
            double probability = random.nextDouble() / Math.pow(difficulty, difficulty / 2);
            double probability2 = random.nextDouble() / Math.pow(difficulty, difficulty / 2);
            double probability3 = random.nextDouble() / Math.pow(difficulty, difficulty / 2);
            double probability4 = random.nextDouble() / Math.pow(difficulty, difficulty / 2);
            double probability5 = random.nextDouble() / Math.pow(difficulty, difficulty / 2);
			double probability6 = random.nextDouble() / Math.pow(difficulty, difficulty / 2);

            for(int b = 0; b < 1 + unlockedZombie; ++b) { // 100% x2 Browncoat
                BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(BasicGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.5) { // 50% x2 Conehead
                for(int c = 0; c < 1 + (unlockedZombie * 2); ++c) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    ConeheadEntity coneheadEntity = (ConeheadEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    coneheadEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
            if (probability2 <= 0.3) { // 30% x1 Buckethead
                for(int u = 0; u < unlockedZombie * 2; ++u) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    BucketheadEntity bucketheadEntity = (BucketheadEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.world);
                    bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    bucketheadEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(bucketheadEntity);
                }
            }
            if (probability3 <= 0.3) { // 30% x1 Pole Vaulting Zombie
                for(int p = 0; p < 1 + (unlockedZombie * 2); ++p) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
                    poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    poleVaultingEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
                }
            }
            if (probability5 <= 0.2) { // 20% x1 Flag Zombie
                for(int f = 0; f < unlockedZombie; ++f) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    FlagzombieEntity flagzombieEntity = (FlagzombieEntity) PvZEntity.FLAGZOMBIE.create(BasicGraveEntity.this.world);
                    flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    flagzombieEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    flagzombieEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(flagzombieEntity);
                }
            }
            if (probability4 <= 0.15) { // 15% x1 Pole Vaulting Zombie
                for(int p = 0; p < 1 + (unlockedZombie * 2); ++p) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
                    poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    poleVaultingEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
                }
            }
			if (probability6 <= 0.05) { // 5% x1 Gargantuar
				for(int g = 0; g < unlockedZombie; ++g) {
					BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
					GargantuarEntity gargantuarEntity = (GargantuarEntity) PvZEntity.GARGANTUAR.create(BasicGraveEntity.this.world);
					gargantuarEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					gargantuarEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					gargantuarEntity.setOwner(BasicGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(gargantuarEntity);
				}
			}
			++this.basicGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected GraveEntity.Spell getSpell() {
            return GraveEntity.Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return BasicGraveEntity.this.owner != null && BasicGraveEntity.this.owner.getTarget() != null && this.canTrack(BasicGraveEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            BasicGraveEntity.this.setTarget(BasicGraveEntity.this.owner.getTarget());
            super.start();
        }
    }

}
