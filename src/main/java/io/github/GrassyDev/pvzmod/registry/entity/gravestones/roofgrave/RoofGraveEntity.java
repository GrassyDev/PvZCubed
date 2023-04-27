package io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basketballcarrier.BasketballCarrierEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
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

public class RoofGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public RoofGraveEntity(EntityType<RoofGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new RoofGraveEntity.summonZombieGoal(this));
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

	public static DefaultAttributeContainer.Builder createRoofGraveAttributes() {
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

	public static boolean canRoofGraveSpawn(EntityType<? extends RoofGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 48000 &&
					pos.getY() > 50 &&
					world.getLocalDifficulty(pos).getLocalDifficulty() >= 1.8 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 42000 &&
					world.getLocalDifficulty(pos).getLocalDifficulty() >= 1.8 &&
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
			LivingEntity livingEntity = RoofGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (RoofGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return RoofGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = RoofGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			RoofGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = RoofGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				RoofGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			RoofGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				RoofGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				RoofGraveEntity.this.playSound(RoofGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.ROOFGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends RoofGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final RoofGraveEntity roofGraveEntity;

		private summonZombieGoal(RoofGraveEntity roofGraveEntity) {
            super();
			this.roofGraveEntity = roofGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            return super.canStart();
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 600;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) RoofGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.roofGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
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

			int zombiePos = -2 + RoofGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + RoofGraveEntity.this.random.nextInt(5);
			if (RoofGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int b = 0; b < 3; ++b) { // 100% x3 Browncoat
				BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
				BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.world);
				browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				browncoatEntity.setOwner(RoofGraveEntity.this);
				serverWorld.spawnEntityAndPassengers(browncoatEntity);
			}
			if (probability <= 0.5) { // 60% x2 Conehead
				for(int c = 0; c < 2; ++c) {
					BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.world);
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					coneheadEntity.setOwner(RoofGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
			}
			if (probability11 <= 0.5) { // 60% x2 Conehead
				for(int c = 0; c < 2; ++c) {
					BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.world);
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					coneheadEntity.setOwner(RoofGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.4) { // 40% x2 Buckethead
					for (int u = 0; u < 2; ++u) {
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.world);
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(RoofGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
				}
				if (probability21 <= 0.4) { // 50% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.world);
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(RoofGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
				}
				if (probability3 <= 0.4) { // 50% x2 Imps
					for (int h = 0; h < 2; ++h) {
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						ImpEntity impEntity = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.world);
						impEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						impEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						impEntity.setOwner(RoofGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(impEntity);
					}
				}
				if (probability8 <= 0.4) { // 50% x3 Imps
					for (int i = 0; i < 3; ++i) {
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						ImpEntity impEntity = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.world);
						impEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						impEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						impEntity.setOwner(RoofGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(impEntity);
					}
				}
				if (difficulty >= 1.859 + difficultymodifier || isUnlock()) {
					if (probability4 <= 0.50) { // 60% x1 Basketball Carrier Zombie
						for (int p = 0; p < 1; ++p) {
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BasketballCarrierEntity basketballCarrierEntity = (BasketballCarrierEntity) PvZEntity.BASKETBALLCARRIER.create(RoofGraveEntity.this.world);
							basketballCarrierEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							basketballCarrierEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							basketballCarrierEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(basketballCarrierEntity);
						}
					}
				}
				if (difficulty >= 1.529 + difficultymodifier || isUnlock()) {
					if (probability5 <= 0.15) { // 15% x1 Flag Zombie
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
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(RoofGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);

							BlockPos blockPos4 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.world);
							bucketheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bucketheadEntity);

							BlockPos blockPos5 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ImpEntity impEntity = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.world);
							impEntity.refreshPositionAndAngles(blockPos5, 0.0F, 0.0F);
							impEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos5), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							impEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(impEntity);

							BlockPos blockPos6 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ImpEntity impEntity2 = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.world);
							impEntity2.refreshPositionAndAngles(blockPos6, 0.0F, 0.0F);
							impEntity2.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos6), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							impEntity2.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(impEntity2);
						}
					}
				}
				if (difficulty >= 1.89 + difficultymodifier || isUnlock()) {
					if (probability6 <= 0.6) { // 70% x2 Basketball Carrier Zombie
						for (int p = 0; p < 2; ++p) {
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BasketballCarrierEntity basketballCarrierEntity = (BasketballCarrierEntity) PvZEntity.BASKETBALLCARRIER.create(RoofGraveEntity.this.world);
							basketballCarrierEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							basketballCarrierEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							basketballCarrierEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(basketballCarrierEntity);
						}
					}
				}
				if (difficulty >= 1.89 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.35) { // 35% x1/x2 Gargantuar
						int xx = 1;
						if (difficulty >= 2.09) {
							xx = 2;
						}
						for (int g = 0; g < xx; ++g) {
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							GargantuarEntity gargantuarEntity = (GargantuarEntity) PvZEntity.GARGANTUAR.create(RoofGraveEntity.this.world);
							gargantuarEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							gargantuarEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							gargantuarEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(gargantuarEntity);

							BlockPos blockPos2 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(RoofGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
			}

			++this.roofGraveEntity.spawnCounter;
			WorldChunk chunk1 = this.roofGraveEntity.world.getWorldChunk(this.roofGraveEntity.getBlockPos());
			long time1 = chunk1.getInhabitedTime();
			chunk1.setInhabitedTime(time1 + 3600);

			WorldChunk chunk2 = this.roofGraveEntity.world.getWorldChunk(this.roofGraveEntity.getBlockPos().south(16));
			long time2 = chunk2.getInhabitedTime();
			chunk2.setInhabitedTime(time2 + 3600);

			WorldChunk chunk3 = this.roofGraveEntity.world.getWorldChunk(this.roofGraveEntity.getBlockPos().north(16));
			long time3 = chunk3.getInhabitedTime();
			chunk3.setInhabitedTime(time3 + 3600);

			WorldChunk chunk4 = this.roofGraveEntity.world.getWorldChunk(this.roofGraveEntity.getBlockPos().west(16));
			long time4 = chunk4.getInhabitedTime();
			chunk4.setInhabitedTime(time4 + 3600);

			WorldChunk chunk5 = this.roofGraveEntity.world.getWorldChunk(this.roofGraveEntity.getBlockPos().east(16));
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
