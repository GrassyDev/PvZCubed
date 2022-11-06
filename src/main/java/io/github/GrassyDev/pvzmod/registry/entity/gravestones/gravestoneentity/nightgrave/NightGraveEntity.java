package io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.nightgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker.BerserkerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static net.minecraft.entity.mob.HostileEntity.isSpawnDark;

public class NightGraveEntity extends SummonerEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    public AnimationFactory factory = new AnimationFactory(this);


    public NightGraveEntity(EntityType<NightGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new NightGraveEntity.summonZombieGoal(this));
        this.targetSelector.add(2, new NightGraveEntity.TrackOwnerTargetGoal(this));
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
		if (this.spawnCounter >= 5){
			this.kill();
		}
		if (this.world.isClient && this.isSpellcasting()) {
			SummonerEntity.Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNightGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150D);
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

	public static boolean canNightGraveSpawn(EntityType<? extends NightGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && spawnDark(world, pos, random);
	}

	public static boolean spawnDark(ServerWorldAccess world, BlockPos pos, RandomGenerator random) {
		if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
			return false;
		} else {
			DimensionType dimensionType = world.getDimension();
			int i = dimensionType.getMonsterSpawnBlockLightLimit();
			if (i < 15 && world.getLightLevel(LightType.BLOCK, pos) > i) {
				return false;
			} else {
				int j = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
				return j <= dimensionType.getMonsterSpawnLightLevel().get(random);
			}
		}
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = NightGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (NightGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return NightGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = NightGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			NightGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = NightGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				NightGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			NightGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				NightGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				NightGraveEntity.this.playSound(NightGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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

		private final NightGraveEntity nightGraveEntity;

        private summonZombieGoal(NightGraveEntity nightGraveEntity) {
            super();
			this.nightGraveEntity = nightGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int a = NightGraveEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int h = NightGraveEntity.this.world.getTargets(ConeheadEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int b = NightGraveEntity.this.world.getTargets(NewspaperEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int c = NightGraveEntity.this.world.getTargets(ScreendoorEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int u = NightGraveEntity.this.world.getTargets(FootballEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int p = NightGraveEntity.this.world.getTargets(BerserkerEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int f = NightGraveEntity.this.world.getTargets(DancingZombieEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                return NightGraveEntity.this.random.nextInt(8) + 1 > a &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > h &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > b &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > c &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > u &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > p &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > f  ;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) NightGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.nightGraveEntity.getBlockPos());
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


            for(int a = 0; a < 1 + unlockedZombie; ++a) { // 100% x2 Browncoat
                BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(NightGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.5) { // 50% x2 Conehead
                for (int h = 0; h < 1 + (unlockedZombie * 2); ++h) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    ConeheadEntity coneheadEntity = (ConeheadEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    coneheadEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
            if (probability2 <= 0.5) {  // 50% x2 Newspaper
                for (int b = 0; b < 1 + (unlockedZombie *2); ++b) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.world);
                    newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    newspaperEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    newspaperEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(newspaperEntity);
                }
            }
            if (probability3 <= 0.5) { // 50% x1 Screendoor
                for(int c = 0; c < unlockedZombie * 2; ++c) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    ScreendoorEntity screendoorEntity = (ScreendoorEntity) PvZEntity.SCREEENDOOR.create(NightGraveEntity.this.world);
                    screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    screendoorEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    screendoorEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(screendoorEntity);
                }
            }
            if (probability4 <= 0.25) { // 25% x1 Football
                for(int u = 0; u < (unlockedZombie * 2); ++u) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    FootballEntity footballEntity = (FootballEntity) PvZEntity.FOOTBALL.create(NightGraveEntity.this.world);
                    footballEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    footballEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    footballEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(footballEntity);
                }
            }
            if (probability5 <= 0.1) { // 10% x1 Berserker
                for(int p = 0; p < unlockedZombie; ++p) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    BerserkerEntity berserkerEntity = (BerserkerEntity) PvZEntity.BERSERKER.create(NightGraveEntity.this.world);
                    berserkerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    berserkerEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    berserkerEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(berserkerEntity);
                }
            }
            if (probability6 <= 0.25) { // 25% x1 Dancing Zombie
                for(int f = 0; f < 1 + unlockedZombie; ++f) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    DancingZombieEntity dancingZombieEntity = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(NightGraveEntity.this.world);
                    dancingZombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    dancingZombieEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    dancingZombieEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(dancingZombieEntity);
                }
            }
			++this.nightGraveEntity.spawnCounter;
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
            return NightGraveEntity.this.owner != null && NightGraveEntity.this.owner.getTarget() != null && this.canTrack(NightGraveEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            NightGraveEntity.this.setTarget(NightGraveEntity.this.owner.getTarget());
            super.start();
        }
    }

}
