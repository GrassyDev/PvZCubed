package io.github.GrassyDev.pvzmod.registry.zombies.zombieentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.*;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.*;
import io.github.GrassyDev.pvzmod.registry.plants.planttypes.*;
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

public class BucketheadEntity extends HostileEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";
    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER;
    private final BreakDoorGoal breakDoorsGoal;
    private boolean canBreakDoors;

    double tonguechance = this.random.nextDouble();

    public BucketheadEntity(EntityType<? extends BucketheadEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
        this.experiencePoints = 12;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (tonguechance <= 0.5) {
            if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("newbrowncoat.walking", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("newbrowncoat.idle", true));
            }
        }
        else {
            if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("newbrowncoat.walking2", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("newbrowncoat.idle2", true));
            }
        }
        return PlayState.CONTINUE;
    }

    public BucketheadEntity(World world) {
        this(PvZEntity.BUCKETHEAD, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new BucketheadEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new BucketheadAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal(this, UnarmedPotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal(this, PotatomineEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, ReinforceEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, EnforceEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal(this, HypnoshroomEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal(this, EnchantEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, PlayerEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal(this, PepperEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, WinterEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, BombardEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, AilmentEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, EnlightenEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, FilamentEntity.class, false, true));
        this.targetSelector.add(4, new TargetGoal(this, MerchantEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, IronGolemEntity.class, false, true));
        ////////// Hypnotized Zombie targets ///////
        this.targetSelector.add(1, new TargetGoal(this, HypnoZombieEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoSummonerEntity.class, false, true));
    }

    public static DefaultAttributeContainer.Builder createBucketheadAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.15D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 137D);
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
                HypnoBucketheadEntity hypnoBucketheadEntity = (HypnoBucketheadEntity) PvZEntity.HYPNOBUCKETHEAD.create(world);
                hypnoBucketheadEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoBucketheadEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoBucketheadEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoBucketheadEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    hypnoBucketheadEntity.setCustomName(this.getCustomName());
                    hypnoBucketheadEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoBucketheadEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoBucketheadEntity);
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

        if (entityData instanceof BucketheadEntity.ZombieData) {
            BucketheadEntity.ZombieData zombieData = (BucketheadEntity.ZombieData)entityData;

            this.setCanBreakDoors(this.shouldBreakDoors() && this.random.nextFloat() < f * 0.1F);
        }
        return (EntityData)entityData;
    }

    public static boolean method_29936(Random random) {
        return random.nextFloat() < 0.05F;
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

    public static boolean canBucketheadSpawn(EntityType<BucketheadEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
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
            return BucketheadEntity.this.owner != null && BucketheadEntity.this.owner.getTarget() != null && this.canTrack(BucketheadEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            BucketheadEntity.this.setTarget(BucketheadEntity.this.owner.getTarget());
            super.start();
        }
    }

}
