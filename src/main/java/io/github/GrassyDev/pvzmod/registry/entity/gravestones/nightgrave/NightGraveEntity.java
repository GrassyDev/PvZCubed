package io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class NightGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public NightGraveEntity(EntityType<NightGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new NightGraveEntity.summonZombieGoal(this));
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
			GraveEntity.Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.world.addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNightGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.nightGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}






	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canNightGraveSpawn(EntityType<? extends NightGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 12000 &&
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
					world.toServerWorld().getTime() > 12000 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}

	/** /~*~//~*GOALS*~//~*~/ **/

	protected SoundEvent getCastSpellSound() {
        return PvZSounds.ENTITYRISINGEVENT;
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

		protected abstract GraveEntity.Spell getSpell();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.NIGHTGRAVESPAWN.getDefaultStack();
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
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 400;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) NightGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.nightGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
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

			int zombiePos = -2 + NightGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + NightGraveEntity.this.random.nextInt(5);
			if (NightGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int a = 0; a < 2 / halfModifier; ++a) { // 100% x2 Browncoat
				if (!NightGraveEntity.this.is1x1()) {
					zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
					zombiePos = NightGraveEntity.this.random.range(-1, 1);
				}
                BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(NightGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.35 / halfModifier) { // 35% x1 Conehead
                for (int h = 0; h < 1; ++h) {
					if (!NightGraveEntity.this.is1x1()) {
						zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
						zombiePos = NightGraveEntity.this.random.range(-1, 1);
					}
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    coneheadEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
			if (probability2 <= 0.15) {  // 15% x1 Newspaper
				for (int b = 0; b < 1; ++b) {
					if (!NightGraveEntity.this.is1x1()) {
						zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
						zombiePos = NightGraveEntity.this.random.range(-1, 1);
					}
					BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.world);
					newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					newspaperEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					newspaperEntity.setOwner(NightGraveEntity.this);
					newspaperEntity.createShield();
					serverWorld.spawnEntityAndPassengers(newspaperEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability11 <= 0.15 / halfModifier) { // 15% x1 Conehead
					for (int h = 0; h < 2 / halfModifier; ++h) {
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(NightGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
					if (probability5 <= 0.15 / halfModifier) { // 15% x1 Flag Zombie
						for (int f = 0; f < 1; ++f) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
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
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(NightGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);

							BlockPos blockPos4 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity screendoorEntity = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.world);
							screendoorEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							screendoorEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							screendoorEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(screendoorEntity);
						}
					}
				}
				if (probability3 <= 0.10 / halfModifier) { // 10% x1 Screendoor
					for (int c = 0; c < 1; ++c) {
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity screendoorEntity = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.world);
						screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						screendoorEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						screendoorEntity.setOwner(NightGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(screendoorEntity);

						BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
						coneheadEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(NightGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
					if (probability9 <= 0.2 / halfModifier) { // 20% x2 Super-Fan Imp
						for (int j = 0; j < 2 / halfModifier; ++j) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							SuperFanImpEntity superFanImpEntity = (SuperFanImpEntity) PvZEntity.SUPERFANIMP.create(NightGraveEntity.this.world);
							superFanImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							superFanImpEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							superFanImpEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(superFanImpEntity);
						}
					}
				}
				if (difficulty >= 1.539 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
					if (probability4 <= 0.25 / halfModifier) { // 25% x1 Football
						for (int u = 0; u < 1; ++u) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FootballEntity footballEntity = (FootballEntity) PvZEntity.FOOTBALL.create(NightGraveEntity.this.world);
							footballEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							footballEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							footballEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(footballEntity);

							BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.539 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
					if (probability6 <= 0.2 / halfModifier) { // 20% x1 Dancing Zombie
						for (int f = 0; f < 1; ++f) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							DancingZombieEntity dancingZombieEntity = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(NightGraveEntity.this.world);
							dancingZombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							dancingZombieEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							dancingZombieEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(dancingZombieEntity);
						}
					}
				}
				if ((difficulty >= 1.909 + difficultymodifier && isUnlockSpecial()) || isUnlock()) {
					if (probability7 <= 0.25 / halfModifier) { // 25% x1 Berserker
						for (int p = 0; p < 1; ++p) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FootballEntity berserkerEntity = (FootballEntity) PvZEntity.BERSERKER.create(NightGraveEntity.this.world);
							berserkerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							berserkerEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							berserkerEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(berserkerEntity);

							BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(NightGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if ((difficulty >= 1.909 + difficultymodifier && isUnlockSpecial()) || isUnlock()) {
					if (probability10 <= 0.3 / halfModifier) { // 30% x1 Sunday Edition and x1 Newspaper
						for (int l = 0; l < 1; ++l) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.world);
							newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							newspaperEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							newspaperEntity.setOwner(NightGraveEntity.this);
							newspaperEntity.createShield();
							serverWorld.spawnEntityAndPassengers(newspaperEntity);
							BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							NewspaperEntity newspaperEntity2 = (NewspaperEntity) PvZEntity.SUNDAYEDITION.create(NightGraveEntity.this.world);
							newspaperEntity2.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							newspaperEntity2.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							newspaperEntity2.setOwner(NightGraveEntity.this);
							newspaperEntity2.createShield();
							serverWorld.spawnEntityAndPassengers(newspaperEntity2);
						}
					}
				}
				if (difficulty >= 1.809 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
					if (probability8 <= 0.3 / halfModifier) { // 30% x2 New Years Imp
						for (int j = 0; j < 2 / halfModifier; ++j) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							SuperFanImpEntity superFanImpEntity = (SuperFanImpEntity) PvZEntity.NEWYEARIMP.create(NightGraveEntity.this.world);
							superFanImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							superFanImpEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							superFanImpEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(superFanImpEntity);
						}
					}
				}
				if (difficulty >= 1.909 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.2 / halfModifier) { // 20% x1 Defensive End
						for (int k = 0; k < 1; ++k) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							GargantuarEntity defensiveEndEntity = (GargantuarEntity) PvZEntity.DEFENSIVEEND.create(NightGraveEntity.this.world);
							defensiveEndEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							defensiveEndEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							defensiveEndEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(defensiveEndEntity);

							BlockPos blockPos2 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
							browncoatEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity);

							BlockPos blockPos3 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity2 = (BrowncoatEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
							browncoatEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							browncoatEntity2.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity2.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(browncoatEntity2);

							BlockPos blockPos4 = NightGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(NightGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
			}
			++this.nightGraveEntity.spawnCounter;
			WorldChunk chunk1 = this.nightGraveEntity.world.getWorldChunk(this.nightGraveEntity.getBlockPos());
			long time1 = chunk1.getInhabitedTime();
			chunk1.setInhabitedTime(time1 + 2400);

			WorldChunk chunk2 = this.nightGraveEntity.world.getWorldChunk(this.nightGraveEntity.getBlockPos().south(16));
			long time2 = chunk2.getInhabitedTime();
			chunk2.setInhabitedTime(time2 + 2400);

			WorldChunk chunk3 = this.nightGraveEntity.world.getWorldChunk(this.nightGraveEntity.getBlockPos().north(16));
			long time3 = chunk3.getInhabitedTime();
			chunk3.setInhabitedTime(time3 + 2400);

			WorldChunk chunk4 = this.nightGraveEntity.world.getWorldChunk(this.nightGraveEntity.getBlockPos().west(16));
			long time4 = chunk4.getInhabitedTime();
			chunk4.setInhabitedTime(time4 + 2400);

			WorldChunk chunk5 = this.nightGraveEntity.world.getWorldChunk(this.nightGraveEntity.getBlockPos().east(16));
			long time5 = chunk5.getInhabitedTime();
			chunk5.setInhabitedTime(time5 + 2400);
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
