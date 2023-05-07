package io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.explorer.ExplorerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.mummy.FlagMummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pharaoh.PharaohEntity;
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

public class EgyptGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;

    private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public EgyptGraveEntity(EntityType<EgyptGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new EgyptGraveEntity.summonZombieGoal(this));
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

	public static DefaultAttributeContainer.Builder createEgyptGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.egyptGraveH());
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

	public static boolean canEgyptGraveSpawn(EntityType<? extends EgyptGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 8500  &&
					pos.getY() > 50 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 8500 &&
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
			LivingEntity livingEntity = EgyptGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (EgyptGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return EgyptGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = EgyptGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			EgyptGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = EgyptGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				EgyptGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			EgyptGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				EgyptGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				EgyptGraveEntity.this.playSound(EgyptGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.EGYPTGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends EgyptGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final EgyptGraveEntity egyptGraveEntity;

		private summonZombieGoal(EgyptGraveEntity egyptGraveEntity) {
            super();
			this.egyptGraveEntity = egyptGraveEntity;
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
            ServerWorld serverWorld = (ServerWorld) EgyptGraveEntity.this.world;
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.egyptGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
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

			int zombiePos = -2 + EgyptGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + EgyptGraveEntity.this.random.nextInt(5);
			if (EgyptGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

            for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
				if (!EgyptGraveEntity.this.is1x1()) {
					zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
					zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
				}
                BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
                MummyEntity mummyEntity = (MummyEntity)PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
                mummyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                mummyEntity.setOwner(EgyptGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(mummyEntity);
            }
            if (probability <= 0.25 / halfModifier) { // 25% x1 Conehead
                for(int c = 0; c < 1; ++c) {
					if (!EgyptGraveEntity.this.is1x1()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
						zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
					}
                    BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    coneheadEntity.setOwner(EgyptGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
			if (probability2 <= 0.10 / halfModifier) { // 10% x1 Buckethead
				for(int u = 0; u < 1; ++u) {
					if (!EgyptGraveEntity.this.is1x1()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
						zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
					}
					BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					MummyEntity bucketheadEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.world);
					bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					bucketheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					bucketheadEntity.setOwner(EgyptGraveEntity.this);
					serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.15 / halfModifier) { // 15% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability21 <= 0.10 / halfModifier) { // 10% x2 Buckethead
						for (int u = 0; u < 2 / halfModifier; ++u) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity bucketheadEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.world);
							bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(bucketheadEntity);
						}
					}
				}
				if (probability3 <= 0.15 / halfModifier) { // 15% x1 Explorer
					for (int p = 0; p < 1; ++p) {
						if (!EgyptGraveEntity.this.is1x1()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
							zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
						}
						BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						ExplorerEntity explorerEntity = (ExplorerEntity) PvZEntity.EXPLORER.create(EgyptGraveEntity.this.world);
						explorerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						explorerEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						explorerEntity.setOwner(EgyptGraveEntity.this);
						serverWorld.spawnEntityAndPassengers(explorerEntity);
					}
				}
				if (difficulty >= 1.529 + difficultymodifier || isUnlock()) {
					if (probability4 <= 0.15 / halfModifier) { // 15% x1 Flag Zombie
						for (int f = 0; f < 1; ++f) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							FlagMummyEntity flagzombieEntity = (FlagMummyEntity) PvZEntity.FLAGMUMMY.create(EgyptGraveEntity.this.world);
							flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							flagzombieEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							flagzombieEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(flagzombieEntity);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);

							BlockPos blockPos4 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability5 <= 0.25 / halfModifier) { // 25% x2 Explorer
						for (int p = 0; p < 2 / halfModifier; ++p) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ExplorerEntity explorerEntity = (ExplorerEntity) PvZEntity.EXPLORER.create(EgyptGraveEntity.this.world);
							explorerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							explorerEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							explorerEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(explorerEntity);
						}
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability6 <= 0.4 / halfModifier) { // 40% x1 Undying Zombie
						for (int g = 0; g < 2 / halfModifier; ++g) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PharaohEntity pharaohEntity = (PharaohEntity) PvZEntity.UNDYINGPHARAOH.create(EgyptGraveEntity.this.world);
							pharaohEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							pharaohEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pharaohEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pharaohEntity);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PharaohEntity pharaohEntity1 = (PharaohEntity) PvZEntity.PHARAOH.create(EgyptGraveEntity.this.world);
							pharaohEntity1.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							pharaohEntity1.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pharaohEntity1.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pharaohEntity1);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);

							BlockPos blockPos4 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.15 / halfModifier) { // 15% x1 Undying Zombie
						for (int h = 0; h < 1; ++h) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PharaohEntity pharaohEntity = (PharaohEntity) PvZEntity.UNDYINGPHARAOH.create(EgyptGraveEntity.this.world);
							pharaohEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							pharaohEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pharaohEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pharaohEntity);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PharaohEntity pharaohEntity1 = (PharaohEntity) PvZEntity.PHARAOH.create(EgyptGraveEntity.this.world);
							pharaohEntity1.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							pharaohEntity1.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pharaohEntity1.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pharaohEntity1);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);

							BlockPos blockPos4 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							coneheadEntity.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.659 + difficultymodifier || isUnlock()) {
					if (probability8 <= 0.3 / halfModifier) { // 30% x1 Torchlight Zombie
						for (int g = 0; g < 1; ++g) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ExplorerEntity torchlight = (ExplorerEntity) PvZEntity.TORCHLIGHT.create(EgyptGraveEntity.this.world);
							torchlight.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							torchlight.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							torchlight.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(torchlight);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);
						}
					}
				}
				if (difficulty >= 1.709 + difficultymodifier || isUnlock()) {
					if (probability9 <= 0.3 / halfModifier) { // 30% x2 Torchlight Zombie
						for (int g = 0; g < Math.round(3 / halfModifier); ++g) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ExplorerEntity torchlight = (ExplorerEntity) PvZEntity.TORCHLIGHT.create(EgyptGraveEntity.this.world);
							torchlight.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							torchlight.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							torchlight.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(torchlight);
						}
						for (int g1 = 0; g1 < 2 / halfModifier; ++g1) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);

							BlockPos blockPos4 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity3 = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							mummyEntity3.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							mummyEntity3.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity3.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity3);
						}
					}
				}
				if (difficulty >= 1.609 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.4 / halfModifier) { // 40% x1 Pyramid Zombie
						if (difficulty >= 1.709) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity pyramidHead = (MummyEntity) PvZEntity.PYRAMIDHEAD.create(EgyptGraveEntity.this.world);
							pyramidHead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							pyramidHead.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pyramidHead.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pyramidHead);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);
						}
						for (int j = 0; j < 1; ++j) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity pyramidHead = (MummyEntity) PvZEntity.PYRAMIDHEAD.create(EgyptGraveEntity.this.world);
							pyramidHead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							pyramidHead.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							pyramidHead.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(pyramidHead);

							BlockPos blockPos2 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.world);
							mummyEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							mummyEntity.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos2), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity);

							BlockPos blockPos3 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity2 = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							mummyEntity2.refreshPositionAndAngles(blockPos3, 0.0F, 0.0F);
							mummyEntity2.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos3), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity2.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity2);

							BlockPos blockPos4 = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity mummyEntity3 = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.world);
							mummyEntity3.refreshPositionAndAngles(blockPos4, 0.0F, 0.0F);
							mummyEntity3.initialize(serverWorld, EgyptGraveEntity.this.world.getLocalDifficulty(blockPos4), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							mummyEntity3.setOwner(EgyptGraveEntity.this);
							serverWorld.spawnEntityAndPassengers(mummyEntity3);
						}
					}
				}
			}
			++this.egyptGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
