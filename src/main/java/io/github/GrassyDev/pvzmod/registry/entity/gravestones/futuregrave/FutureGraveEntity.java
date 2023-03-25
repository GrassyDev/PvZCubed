package io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack.JetpackEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FutureGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public FutureGraveEntity(EntityType<FutureGraveEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 25;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
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
		if (beingEaten){
			event.getController().setAnimation(new AnimationBuilder().loop("obstacle.eating"));
		}
		else if (tiltchance <= 0.5) {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle2"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new FutureGraveEntity.summonZombieGoal(this));
        this.targetSelector.add(2, new FutureGraveEntity.TrackOwnerTargetGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.setTarget(this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
		LocalDifficulty localDifficulty = world.getLocalDifficulty(this.getBlockPos());
		double difficulty = localDifficulty.getLocalDifficulty();
		if (this.spawnCounter == 1 && world.getTime() < 24000) {
			this.kill();
		}
		else if (this.spawnCounter == 2 && difficulty <= 1.509){
			this.kill();
		}
		else if (this.spawnCounter == 3 && difficulty <= 1.609){
			this.kill();
		}
		else if (this.spawnCounter == 4 && difficulty <= 1.809){
			this.kill();
		}
		else if (this.spawnCounter == 5 && difficulty <= 2.309){
			this.kill();
		}
		else if (this.spawnCounter == 6 && difficulty >= 2.909){
			this.kill();
		}
		else if (this.spawnCounter > 7){
			this.kill();
		}
		if (this.world.isClient && this.isSpellcasting()) {
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFutureGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 240D);
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

	public static boolean canFutureGraveSpawn(EntityType<? extends FutureGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return world.getDifficulty() != Difficulty.PEACEFUL &&
				world.toServerWorld().getTime() > 48000 &&
				world.getLocalDifficulty(pos).getLocalDifficulty() >= 1.6 &&
				canMobSpawn(type, world, spawnReason, pos, random) &&
				pos.getY() >= 50 &&
				world.getBlockState(blockPos).allowsSpawning(world, blockPos, type) &&
				!checkVillager(Vec3d.ofCenter(pos), world) &&
				!checkPlant(Vec3d.ofCenter(pos), world);
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = FutureGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (FutureGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return FutureGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = FutureGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			FutureGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = FutureGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				FutureGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			FutureGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				FutureGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				FutureGraveEntity.this.playSound(FutureGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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

		protected abstract Spell getSpell();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.FUTUREGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends FutureGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final FutureGraveEntity futureGraveEntity;

		private summonZombieGoal(FutureGraveEntity futureGraveEntity) {
            super();
			this.futureGraveEntity = futureGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
			return super.canStart();
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 500;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) FutureGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.futureGraveEntity.getBlockPos());
			double difficulty = localDifficulty.getLocalDifficulty();
			double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability21 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability3 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability7 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

            for(int b = 0; b < 2; ++b) { // 100% x2 Browncoat
                BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(FutureGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(FutureGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.35) { // 35% x1 Conehead
                for(int c = 0; c < 1; ++c) {
                    BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(FutureGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    coneheadEntity.setOwner(FutureGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
            if (probability2 <= 0.20) { // 20% x1 Buckethead
                for(int h = 0; h < 1; ++h) {
                    BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
                    BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(FutureGraveEntity.this.world);
                    bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    bucketheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    bucketheadEntity.setOwner(FutureGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(bucketheadEntity);
                }
            }
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability11 <= 0.25) { // 25% x1 Conehead
					for (int d = 0; d < 1; ++d) {
						BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(FutureGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(FutureGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (probability21 <= 0.15) { // 15% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(FutureGraveEntity.this.world);
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(FutureGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
				}
				if (probability3 <= 0.4) { // 40% x2 Jetpack Zombie
					for (int p = 0; p < 2; ++p) {
						BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
						JetpackEntity jetpackEntity = (JetpackEntity) PvZEntity.JETPACK.create(FutureGraveEntity.this.world);
						jetpackEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						jetpackEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						jetpackEntity.setOwner(FutureGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(jetpackEntity);
					}
				}
				if (difficulty >= 1.529 + difficultymodifier) {
					if (probability5 <= 0.25) { // 25% x2 Flag Zombie
						for (int f = 0; f < 2; ++f) {
							double random = Math.random();
							EntityType<?> flagType;
							if (random <= 0.125) {
								flagType = PvZEntity.FLAGZOMBIE_G;
							} else if (random <= 0.25) {
								flagType = PvZEntity.FLAGZOMBIE_T;
							} else {
								flagType = PvZEntity.FLAGZOMBIE;
							}
							BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
							FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(FutureGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(FutureGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(FutureGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(FutureGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
							JetpackEntity jetpackEntity = (JetpackEntity) PvZEntity.JETPACK.create(FutureGraveEntity.this.world);
							jetpackEntity.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							jetpackEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							jetpackEntity.setOwner(FutureGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(jetpackEntity);

							BlockPos blockPos4 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(FutureGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(FutureGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.529 + difficultymodifier) {
					if (probability4 <= 0.40) { // 50% x1 Blastronaut Zombie
						for (int p = 0; p < 1; ++p) {
							BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
							JetpackEntity blastronautEntity = (JetpackEntity) PvZEntity.BLASTRONAUT.create(FutureGraveEntity.this.world);
							blastronautEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							blastronautEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							blastronautEntity.setOwner(FutureGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(blastronautEntity);
						}
					}
				}
				/**
				 if (difficulty >= 1.89 + difficultymodifier) {
				 if (probability6 <= 0.085) { // 8.5% x1 Gargantuar
				 for (int g = 0; g < 1; ++g) {
				 BlockPos blockPos = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
				 GargantuarEntity gargantuarEntity = (GargantuarEntity) PvZEntity.GARGANTUAR.create(FutureGraveEntity.this.world);
				 gargantuarEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				 gargantuarEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				 gargantuarEntity.setOwner(FutureGraveEntity.this);
				 serverWorld.spawnEntityAndPassengers(gargantuarEntity);

				 BlockPos blockPos2 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
				 BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(FutureGraveEntity.this.world);
				 browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
				 browncoatEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				 browncoatEntity.setOwner(FutureGraveEntity.this);
				 serverWorld.spawnEntityAndPassengers(browncoatEntity);

				 BlockPos blockPos3 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
				 BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(FutureGraveEntity.this.world);
				 browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
				 browncoatEntity2.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				 browncoatEntity2.setOwner(FutureGraveEntity.this);
				 serverWorld.spawnEntityAndPassengers(browncoatEntity2);

				 BlockPos blockPos4 = FutureGraveEntity.this.getBlockPos().add(-2 + FutureGraveEntity.this.random.nextInt(5), 0.1, -2 + FutureGraveEntity.this.random.nextInt(5));
				 BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(FutureGraveEntity.this.world);
				 coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
				 coneheadEntity.initialize(serverWorld, FutureGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				 coneheadEntity.setOwner(FutureGraveEntity.this);
				 serverWorld.spawnEntityAndPassengers(coneheadEntity);
				 }
				 }
				 }**/
			}

			++this.futureGraveEntity.spawnCounter;
			WorldChunk chunk1 = this.futureGraveEntity.world.getWorldChunk(this.futureGraveEntity.getBlockPos());
			long time1 = chunk1.getInhabitedTime();
			chunk1.setInhabitedTime(time1 + 2400);

			WorldChunk chunk2 = this.futureGraveEntity.world.getWorldChunk(this.futureGraveEntity.getBlockPos().south(16));
			long time2 = chunk2.getInhabitedTime();
			chunk2.setInhabitedTime(time2 + 2400);

			WorldChunk chunk3 = this.futureGraveEntity.world.getWorldChunk(this.futureGraveEntity.getBlockPos().north(16));
			long time3 = chunk3.getInhabitedTime();
			chunk3.setInhabitedTime(time3 + 2400);

			WorldChunk chunk4 = this.futureGraveEntity.world.getWorldChunk(this.futureGraveEntity.getBlockPos().west(16));
			long time4 = chunk4.getInhabitedTime();
			chunk4.setInhabitedTime(time4 + 2400);

			WorldChunk chunk5 = this.futureGraveEntity.world.getWorldChunk(this.futureGraveEntity.getBlockPos().east(16));
			long time5 = chunk5.getInhabitedTime();
			chunk5.setInhabitedTime(time5 + 2400);
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
            return FutureGraveEntity.this.owner != null && FutureGraveEntity.this.owner.getTarget() != null && this.canTrack(FutureGraveEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            FutureGraveEntity.this.setTarget(FutureGraveEntity.this.owner.getTarget());
            super.start();
        }
    }

}
