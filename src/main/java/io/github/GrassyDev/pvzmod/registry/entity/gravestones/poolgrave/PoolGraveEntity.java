package io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dolphinrider.DolphinRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
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

import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PoolGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public PoolGraveEntity(EntityType<PoolGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new PoolGraveEntity.summonZombieGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.setTarget(this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
		LocalDifficulty localDifficulty = world.getLocalDifficulty(this.getBlockPos());
		double difficulty = 0;
		if (this.getVariant().equals(GraveDifficulty.NONE)){
			difficulty = localDifficulty.getLocalDifficulty();
		}
		else if (this.getVariant().equals(GraveDifficulty.EASY)){
			difficulty = 1.0;
		}
		else if (this.getVariant().equals(GraveDifficulty.EASYMED)){
			difficulty = 1.5;
		}
		else if (this.getVariant().equals(GraveDifficulty.MED)){
			difficulty = 1.6;
		}
		else if (this.getVariant().equals(GraveDifficulty.MEDHARD)){
			difficulty = 1.8;
		}
		else if (this.getVariant().equals(GraveDifficulty.HARD)){
			difficulty = 2.1;
		}
		else if (this.getVariant().equals(GraveDifficulty.SUPERHARD)){
			difficulty = 3;
		}
		else if (this.getVariant().equals(GraveDifficulty.NIGHTMARE)){
			difficulty = 4;
		}
		else if (this.getVariant().equals(GraveDifficulty.CRAAAZY)){
			difficulty = 5;
		}
		if (this.spawnCounter == 1 && world.getTime() < 24000) {
			this.kill();
		}
		if (this.spawnCounter == 1 && world.getTime() < 24000) {
			this.kill();
		}
		else if (this.spawnCounter == 2 && difficulty <= 1.509 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 3 && difficulty <= 1.709 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 4 && difficulty <= 1.909 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 5 && difficulty <= 2.109 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 6 && difficulty >= 2.309 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter > 7){
			if (!this.isInfinite()) {
				this.kill();
			}
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

	public static DefaultAttributeContainer.Builder createPoolGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.poolGraveH());
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

	public static boolean canPoolGraveSpawn(EntityType<? extends PoolGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 8000 &&
					pos.getY() > 50 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 8000 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = PoolGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (PoolGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return PoolGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = PoolGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			PoolGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = PoolGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				PoolGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			PoolGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				PoolGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				PoolGraveEntity.this.playSound(PoolGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.POOLGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends PoolGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final PoolGraveEntity poolGraveEntity;

		private summonZombieGoal(PoolGraveEntity poolGraveEntity) {
            super();
			this.poolGraveEntity = poolGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

		public boolean canStart() {
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 300;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) PoolGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.poolGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.poolGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
				difficulty = 5;
			}
			double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability21 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability3 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability7 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + PoolGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + PoolGraveEntity.this.random.nextInt(5);
			if (PoolGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

            for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
				if (!PoolGraveEntity.this.is1x1()) {
					zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
					zombiePos = PoolGraveEntity.this.random.range(-1, 1);
				}
                BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(PoolGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(PoolGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
			if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
				if (probability <= 0.25 / halfModifier) { // 25% x2 Conehead
					for (int c = 0; c < 2 / halfModifier; ++c) {
						if (!PoolGraveEntity.this.is1x1()) {
							zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
							zombiePos = PoolGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(PoolGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(PoolGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability11 <= 0.10 / halfModifier) { // 10% x3 Conehead
					for (int c = 0; c < Math.round(3 / halfModifier); ++c) {
						if (!PoolGraveEntity.this.is1x1()) {
							zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
							zombiePos = PoolGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(PoolGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(PoolGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability2 <= 0.25 / halfModifier) { // 25% x1 Buckethead
						for (int u = 0; u < 1; ++u) {
							if (!PoolGraveEntity.this.is1x1()) {
								zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
								zombiePos = PoolGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(PoolGraveEntity.this.world);
							bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bucketheadEntity);
						}
					}
				}
				if (probability3 <= 0.3 / halfModifier) { // 30% x1 SnorkelZombie
					for (int p = 0; p < 1; ++p) {
						if (!PoolGraveEntity.this.is1x1()) {
							zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
							zombiePos = PoolGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						SnorkelEntity snorkelEntity = (SnorkelEntity) PvZEntity.SNORKEL.create(PoolGraveEntity.this.world);
						snorkelEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						snorkelEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						snorkelEntity.setOwner(PoolGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(snorkelEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability21 <= 0.15 / halfModifier) { // 15% x4 SnorkelZombie
						for (int p = 0; p < 4 / halfModifier; ++p) {
							if (!PoolGraveEntity.this.is1x1()) {
								zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
								zombiePos = PoolGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							SnorkelEntity snorkelEntity = (SnorkelEntity) PvZEntity.SNORKEL.create(PoolGraveEntity.this.world);
							snorkelEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							snorkelEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							snorkelEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(snorkelEntity);
						}
					}
				}
				if (difficulty >= 1.529 + difficultymodifier || isUnlock()) {
					if (probability4 <= 0.4 / halfModifier) { // 40% x1 Dolphin Rider Zombie
						for (int h = 0; h < 1; ++h) {
							if (!PoolGraveEntity.this.is1x1()) {
								zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
								zombiePos = PoolGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							DolphinRiderEntity dolphinRiderEntity = (DolphinRiderEntity) PvZEntity.DOLPHINRIDER.create(PoolGraveEntity.this.world);
							dolphinRiderEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							dolphinRiderEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							dolphinRiderEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(dolphinRiderEntity);
						}
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability5 <= 0.4 / halfModifier) { // 40% x3 Dolphin Rider Zombie
						for (int j = 0; j < Math.round(3 / halfModifier); ++j) {
							if (!PoolGraveEntity.this.is1x1()) {
								zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
								zombiePos = PoolGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							DolphinRiderEntity dolphinRiderEntity = (DolphinRiderEntity) PvZEntity.DOLPHINRIDER.create(PoolGraveEntity.this.world);
							dolphinRiderEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							dolphinRiderEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							dolphinRiderEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(dolphinRiderEntity);
						}
					}
				}
				if (difficulty >= 1.529 + difficultymodifier || isUnlock()) {
					if (probability6 <= 0.15 / halfModifier) { // 15% x1 Flag Zombie
						for (int g = 0; g < 1; ++g) {
							if (!PoolGraveEntity.this.is1x1()) {
								zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
								zombiePos = PoolGraveEntity.this.random.range(-1, 1);
							}
							double random = Math.random();
							EntityType<?> flagType;
							if (random <= 0.125) {
								flagType = PvZEntity.FLAGZOMBIE_G;
							} else if (random <= 0.25) {
								flagType = PvZEntity.FLAGZOMBIE_T;
							} else {
								flagType = PvZEntity.FLAGZOMBIE;
							}
							BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(PoolGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos5 = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagzombieEntity flagzombieEntity2 = (FlagzombieEntity) flagType.create(PoolGraveEntity.this.world);
							flagzombieEntity2.refreshPositionAndAngles(blockPos5, 0.0F, 0.0F);
							flagzombieEntity2.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos5), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity2.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity2);

							BlockPos blockPos2 = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(PoolGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);

							BlockPos blockPos3 = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(PoolGraveEntity.this.world);
							bucketheadEntity.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bucketheadEntity);

							BlockPos blockPos4 = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							DolphinRiderEntity dolphinRiderEntity = (DolphinRiderEntity) PvZEntity.DOLPHINRIDER.create(PoolGraveEntity.this.world);
							dolphinRiderEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							dolphinRiderEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							dolphinRiderEntity.setOwner(PoolGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(dolphinRiderEntity);
						}
					}
				}
				/**if (difficulty >= 1.89 + difficultymodifier || isUnlock()) {
				 if (probability7 <= 0.085) { // 8.5% x1 Zomboni
				 for (int h = 0; h < 1; ++h) {
				 if (!PoolGraveEntity.this.is1x1()) {
				 zombiePosZ = PoolGraveEntity.this.random.range(-1, 1);
				 zombiePos = PoolGraveEntity.this.random.range(-1, 1);
				 }
				 BlockPos blockPos = PoolGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
				 GargantuarEntity gargantuarEntity = (GargantuarEntity) PvZEntity.GARGANTUAR.create(PoolGraveEntity.this.world);
				 gargantuarEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				 gargantuarEntity.initialize(serverWorld, PoolGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				 gargantuarEntity.setOwner(PoolGraveEntity.this);
				 serverWorld.spawnEntityAndPassengers(gargantuarEntity);
				 }
				 }
				 }**/
			}
			++this.poolGraveEntity.spawnCounter;
			WorldChunk chunk1 = this.poolGraveEntity.world.getWorldChunk(this.poolGraveEntity.getBlockPos());
			long time1 = chunk1.getInhabitedTime();
			chunk1.setInhabitedTime(time1 + 3600);

			WorldChunk chunk2 = this.poolGraveEntity.world.getWorldChunk(this.poolGraveEntity.getBlockPos().south(16));
			long time2 = chunk2.getInhabitedTime();
			chunk2.setInhabitedTime(time2 + 3600);

			WorldChunk chunk3 = this.poolGraveEntity.world.getWorldChunk(this.poolGraveEntity.getBlockPos().north(16));
			long time3 = chunk3.getInhabitedTime();
			chunk3.setInhabitedTime(time3 + 3600);

			WorldChunk chunk4 = this.poolGraveEntity.world.getWorldChunk(this.poolGraveEntity.getBlockPos().west(16));
			long time4 = chunk4.getInhabitedTime();
			chunk4.setInhabitedTime(time4 + 3600);

			WorldChunk chunk5 = this.poolGraveEntity.world.getWorldChunk(this.poolGraveEntity.getBlockPos().east(16));
			long time5 = chunk5.getInhabitedTime();
			chunk5.setInhabitedTime(time5 + 3600);
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
