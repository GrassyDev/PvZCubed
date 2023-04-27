package io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
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

public class BasicGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BasicGraveEntity(EntityType<BasicGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new BasicGraveEntity.summonZombieGoal(this));
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

	public static DefaultAttributeContainer.Builder createBasicGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.basicGraveH());
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
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 6000  &&
					pos.getY() > 50 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 6000 &&
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


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BASICGRAVESPAWN.getDefaultStack();
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
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 300;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)BasicGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.basicGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
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
			double probability8 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability9 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability10 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability12 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + BasicGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + BasicGraveEntity.this.random.nextInt(5);
			if (BasicGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

            for(int b = 0; b < 2; ++b) { // 100% x2 Browncoat
                BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(BasicGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.25) { // 25% x1 Conehead
                for(int c = 0; c < 1; ++c) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    coneheadEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
			if (probability2 <= 0.10) { // 10% x1 Buckethead
				for(int u = 0; u < 1; ++u) {
					BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.world);
					bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					bucketheadEntity.setOwner(BasicGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.15) { // 15% x2 Conehead
						for (int c = 0; c < 2; ++c) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability21 <= 0.10) { // 10% x2 Buckethead
						for (int u = 0; u < 2; ++u) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.world);
							bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bucketheadEntity);
						}
					}
				}
				if (probability3 <= 0.20) { // 20% x1 Pole Vaulting Zombie
					for (int p = 0; p < 1; ++p) {
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
						poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						poleVaultingEntity.setOwner(BasicGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
					}
				}
				if (difficulty >= 1.529 + difficultymodifier || isUnlock()) {
					if (probability4 <= 0.15) { // 15% x1 Flag Zombie
						for (int f = 0; f < 1; ++f) {
							double random = Math.random();
							EntityType<?> flagType;
							if (random <= 0.125) {
								flagType = PvZEntity.FLAGZOMBIE_G;
							} else if (random <= 0.25) {
								flagType = PvZEntity.FLAGZOMBIE_T;
							} else {
								flagType = PvZEntity.FLAGZOMBIE;
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(BasicGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (probability5 <= 0.05) { // 5% x2 Pole Vaulting Zombie
					for (int p = 0; p < 2; ++p) {
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
						poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						poleVaultingEntity.setOwner(BasicGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability6 <= 0.4) { // 40% x2 Bully Zombie
						for (int g = 0; g < 2; ++g) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.world);
							bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bullyEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bullyEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bullyEntity);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.15) { // 15% x2 Bully Zombie
						for (int h = 0; h < 2; ++h) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.world);
							bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bullyEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bullyEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bullyEntity);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability8 <= 0.4) { // 50% x1 Trash Can Zombie
						for (int g = 0; g < 1; ++g) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity trashCan = (BrowncoatEntity) PvZEntity.TRASHCAN.create(BasicGraveEntity.this.world);
							trashCan.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							trashCan.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							trashCan.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(trashCan);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity3 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity3.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							browncoatEntity3.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity3.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity3);
						}
					}
				}
				if (difficulty >= 1.709 + difficultymodifier || isUnlock()) {
					if (probability9 <= 0.4) { // 40% x3 Trash Can Zombie
						for (int g = 0; g < 3; ++g) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity trashCan = (BrowncoatEntity) PvZEntity.TRASHCAN.create(BasicGraveEntity.this.world);
							trashCan.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							trashCan.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							trashCan.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(trashCan);
						}
						for (int g1 = 0; g1 < 2; ++g1) {
							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity3 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity3.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							browncoatEntity3.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity3.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity3);
						}
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.4) { // 40% x1 Brickhead Zombie
						if (difficulty >= 1.709) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity brickhead = (BrowncoatEntity) PvZEntity.BRICKHEAD.create(BasicGraveEntity.this.world);
							brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							brickhead.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							brickhead.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(brickhead);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);
						}
						for (int j = 0; j < 1; ++j) {
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity brickhead = (BrowncoatEntity) PvZEntity.BRICKHEAD.create(BasicGraveEntity.this.world);
							brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							brickhead.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							brickhead.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(brickhead);

							BlockPos blockPos2 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity3 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
							browncoatEntity3.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							browncoatEntity3.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity3.setOwner(BasicGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity3);
						}
					}
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
}
