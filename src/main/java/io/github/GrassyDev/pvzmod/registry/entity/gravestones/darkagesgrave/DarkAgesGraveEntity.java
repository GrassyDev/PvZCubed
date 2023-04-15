package io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.darkages.FlagPeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.announcer.AnnouncerImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
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
import net.minecraft.world.*;
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

public class DarkAgesGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public DarkAgesGraveEntity(EntityType<DarkAgesGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new DarkAgesGraveEntity.summonZombieGoal(this));
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
			Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createDarkAgesGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.darkAgesGraveH());
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

	public static boolean canDarkAgesGraveSpawn(EntityType<? extends DarkAgesGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 24000  &&
					pos.getY() > 50 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 24000 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
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
			LivingEntity livingEntity = DarkAgesGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (DarkAgesGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return DarkAgesGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = DarkAgesGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			DarkAgesGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = DarkAgesGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				DarkAgesGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			DarkAgesGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				DarkAgesGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				DarkAgesGraveEntity.this.playSound(DarkAgesGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.DARKAGESGRAVESPAWN.getDefaultStack();
	}

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final DarkAgesGraveEntity darkAgesGraveEntity;

        private summonZombieGoal(DarkAgesGraveEntity darkAgesGraveEntity) {
            super();
			this.darkAgesGraveEntity = darkAgesGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

		public boolean canStart() {
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 400;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) DarkAgesGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.darkAgesGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
				difficulty = 5;
			}
			double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability10 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability3 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability7 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability9 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability8 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));



			for(int a = 0; a < 2; ++a) { // 100% x2 Peasant
                BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(DarkAgesGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.35) { // 35% x1 Conehead
                for (int h = 0; h < 1; ++h) {
                    BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    coneheadEntity.setOwner(DarkAgesGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
			if (probability3 <= 0.10) { // 10% x1 Buckethead Peasant
				for (int c = 0; c < 1; ++c) {
					BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
					BrowncoatEntity peasantBucket = (BrowncoatEntity) PvZEntity.PEASANTBUCKET.create(DarkAgesGraveEntity.this.world);
					peasantBucket.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					peasantBucket.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					peasantBucket.setOwner(DarkAgesGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(peasantBucket);

					BlockPos blockPos2 = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.world);
					coneheadEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(DarkAgesGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.529 + difficultymodifier) {
					if (probability5 <= 0.15) { // 15% x1 Flag Zombie
						for (int f = 0; f < 1; ++f) {
							double random = Math.random();
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
							FlagPeasantEntity flagzombieEntity = (FlagPeasantEntity) PvZEntity.FLAGPEASANT.create(DarkAgesGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(DarkAgesGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(DarkAgesGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(DarkAgesGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);

							BlockPos blockPos4 = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
							BrowncoatEntity screendoorEntity = (BrowncoatEntity) PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.world);
							screendoorEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							screendoorEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							screendoorEntity.setOwner(DarkAgesGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(screendoorEntity);
						}
					}
				}
				if (probability11 <= 0.15) { // 15% x1 Conehead
					for (int h = 0; h < 2; ++h) {
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(DarkAgesGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (probability2 <= 0.10) { // 10% x1 Peasant Knight
					for (int c = 0; c < 1; ++c) {
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
						BrowncoatEntity peasantKnight = (BrowncoatEntity) PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.world);
						peasantKnight.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						peasantKnight.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						peasantKnight.setOwner(DarkAgesGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(peasantKnight);

						BlockPos blockPos2 = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(DarkAgesGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (probability10 <= 0.3) { // 30% x2 Imp Dragons
					for (int h = 0; h < 2; ++h) {
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
						ImpEntity impEntity = (ImpEntity) PvZEntity.IMPDRAGON.create(DarkAgesGraveEntity.this.world);
						impEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						impEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						impEntity.setOwner(DarkAgesGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(impEntity);
					}
				}
				if (probability4 <= 0.3) { // 30% x2 Imp Dragons
					for (int i = 0; i < 3; ++i) {
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
						ImpEntity impEntity = (ImpEntity) PvZEntity.IMPDRAGON.create(DarkAgesGraveEntity.this.world);
						impEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						impEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						impEntity.setOwner(DarkAgesGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(impEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier) {
					if (probability6 <= 0.2) { // 20% x1 Announcer Imp
						for (int f = 0; f < 1; ++f) {
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(-2 + DarkAgesGraveEntity.this.random.nextInt(5), 0.1, -2 + DarkAgesGraveEntity.this.random.nextInt(5));
							AnnouncerImpEntity announcerImpEntity = (AnnouncerImpEntity) PvZEntity.ANNOUNCERIMP.create(DarkAgesGraveEntity.this.world);
							announcerImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							announcerImpEntity.initialize(serverWorld, DarkAgesGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							announcerImpEntity.setOwner(DarkAgesGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(announcerImpEntity);
						}
					}
				}
			}
			++this.darkAgesGraveEntity.spawnCounter;
			WorldChunk chunk1 = this.darkAgesGraveEntity.world.getWorldChunk(this.darkAgesGraveEntity.getBlockPos());
			long time1 = chunk1.getInhabitedTime();
			chunk1.setInhabitedTime(time1 + 2400);

			WorldChunk chunk2 = this.darkAgesGraveEntity.world.getWorldChunk(this.darkAgesGraveEntity.getBlockPos().south(16));
			long time2 = chunk2.getInhabitedTime();
			chunk2.setInhabitedTime(time2 + 2400);

			WorldChunk chunk3 = this.darkAgesGraveEntity.world.getWorldChunk(this.darkAgesGraveEntity.getBlockPos().north(16));
			long time3 = chunk3.getInhabitedTime();
			chunk3.setInhabitedTime(time3 + 2400);

			WorldChunk chunk4 = this.darkAgesGraveEntity.world.getWorldChunk(this.darkAgesGraveEntity.getBlockPos().west(16));
			long time4 = chunk4.getInhabitedTime();
			chunk4.setInhabitedTime(time4 + 2400);

			WorldChunk chunk5 = this.darkAgesGraveEntity.world.getWorldChunk(this.darkAgesGraveEntity.getBlockPos().east(16));
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
}
