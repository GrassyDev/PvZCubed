package io.github.GrassyDev.pvzmod.registry.zombies.zombieentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoPoleVaultingEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.function.Predicate;

public class PoleVaultingEntity extends HostileEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "runningcontroller";
    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER;
    private final BreakDoorGoal breakDoorsGoal;
    private boolean canBreakDoors;

    public PoleVaultingEntity(EntityType<? extends PoleVaultingEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
        this.experiencePoints = 3;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("polevaulting.running", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("polevaulting.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public PoleVaultingEntity(World world) {
        this(PvZEntity.POLEVAULTING, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new PoleVaultingEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new PoleVaultingAttackGoal(this, 1.0D, true));
        this.goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0D, false, 4, this::canBreakDoors));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, new FollowTargetGoal(this, PlayerEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, GravebusterEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, PeashooterEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, IceshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, DoomshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, ScaredyshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, ThreepeaterEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, SnowpeaEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, SunflowerEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, SunshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, PuffshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, FumeshroomEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, RepeaterEntity.class, false, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, MerchantEntity.class, false, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, false, true));
        ////////// Hypnotized Zombie targets ///////
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoshroomEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoBrowncoatEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoFlagzombieEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoConeheadEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoPoleVaultingEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoBucketheadEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoNewspaperEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoScreendoorEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoFootballEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoBerserkerEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoDancingZombieEntity.class, false, true));
        this.targetSelector.add(1, new FollowTargetGoal(this, HypnoBackupDancerEntity.class, false, true));
    }

    public static DefaultAttributeContainer.Builder createPoleVaultingAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean canBreakDoors) {
        if (this.shouldBreakDoors() && NavigationConditions.hasMobNavigation(this)) {
            if (this.canBreakDoors != canBreakDoors) {
                this.canBreakDoors = canBreakDoors;
                ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(canBreakDoors);
                if (canBreakDoors) {
                    this.goalSelector.add(1, this.breakDoorsGoal);
                } else {
                    this.goalSelector.remove(this.breakDoorsGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.remove(this.breakDoorsGoal);
            this.canBreakDoors = false;
        }

    }

    protected boolean shouldBreakDoors() {
        return true;
    }

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

            if (this.getRecentDamageSource() == DamageSource.OUT_OF_WORLD) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                HypnoPoleVaultingEntity hypnoPoleVaultingEntity = (HypnoPoleVaultingEntity) PvZEntity.HYPNOPOLEVAULTING.create(world);
                hypnoPoleVaultingEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoPoleVaultingEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoPoleVaultingEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoPoleVaultingEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    hypnoPoleVaultingEntity.setCustomName(this.getCustomName());
                    hypnoPoleVaultingEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoPoleVaultingEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoPoleVaultingEntity);
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }
    }

    protected SoundEvent getAmbientSound() {
        return PvZCubed.ZOMBIEMOANEVENT;
    }

    protected SoundEvent getHurtSound() {
        return PvZCubed.SILENCEVENET;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("CanBreakDoors", this.canBreakDoors());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
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

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        float f = difficulty.getClampedLocalDifficulty();

        if (entityData instanceof PoleVaultingEntity.ZombieData) {
            PoleVaultingEntity.ZombieData zombieData = (PoleVaultingEntity.ZombieData)entityData;

            this.setCanBreakDoors(this.shouldBreakDoors() && this.random.nextFloat() < 1F);
        }
        return (EntityData)entityData;
    }

    public static boolean method_29936(Random random) {
        return random.nextFloat() < 1F;
    }

    static {
        DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
            return difficulty == Difficulty.HARD;
        };
    }

    public static class ZombieData implements EntityData {

        public ZombieData(boolean baby, boolean bl) {
        }
    }

    public static boolean canPoleVaultingSpawn(EntityType<PoleVaultingEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 55;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return PoleVaultingEntity.this.owner != null && PoleVaultingEntity.this.owner.getTarget() != null && this.canTrack(PoleVaultingEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            PoleVaultingEntity.this.setTarget(PoleVaultingEntity.this.owner.getTarget());
            super.start();
        }
    }

}