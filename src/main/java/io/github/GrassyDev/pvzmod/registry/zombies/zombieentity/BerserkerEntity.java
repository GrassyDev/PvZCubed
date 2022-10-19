package io.github.GrassyDev.pvzmod.registry.zombies.zombieentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.*;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
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

public class BerserkerEntity extends HostileEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";
    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER;
    private final BreakDoorGoal breakDoorsGoal;
    private boolean canBreakDoors;
    private int attackTicksLeft;
    public boolean firstAttack;
    public boolean tackle;

    public BerserkerEntity(EntityType<? extends BerserkerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
        this.experiencePoints = 12;
        this.firstAttack = true;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {

        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            if (!this.tackle) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("football.running", true));
            }
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("football.tackle", true));
            }
        }else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("football.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public BerserkerEntity(World world) {
        this(PvZEntity.BERSERKER, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new BerserkerEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new BerserkerAttackGoal(this, 1.0D, true));
        this.goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0D, false, 4, this::canBreakDoors));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, new TargetGoal(this, WallnutEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, ChomperEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, GravebusterEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, PlayerEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, PeashooterEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, IceshroomEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, DoomshroomEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, ScaredyshroomEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, ThreepeaterEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, SnowpeaEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, SunflowerEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, SunshroomEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, PotatomineEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, UnarmedPotatomineEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, RepeaterEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, PuffshroomEntity.class, false, true));
        this.targetSelector.add(3, new TargetGoal(this, FumeshroomEntity.class, false, true));
        this.targetSelector.add(4, new TargetGoal(this, MerchantEntity.class, false, true));
        this.targetSelector.add(2, new TargetGoal(this, IronGolemEntity.class, false, true));
        ////////// Hypnotized Zombie targets ///////
        this.targetSelector.add(1, new TargetGoal(this, HypnoshroomEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoBrowncoatEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoFlagzombieEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoConeheadEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoPoleVaultingEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoBucketheadEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoNewspaperEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoScreendoorEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoFootballEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoBerserkerEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoDancingZombieEntity.class, false, true));
        this.targetSelector.add(1, new TargetGoal(this, HypnoBackupDancerEntity.class, false, true));
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }

    public static DefaultAttributeContainer.Builder createBerserkerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.18D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180D);
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
                HypnoBerserkerEntity hypnotizedZombie = (HypnoBerserkerEntity) PvZEntity.HYPNOBERSERKER.create(world);
                hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnotizedZombie.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    hypnotizedZombie.setCustomName(this.getCustomName());
                    hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnotizedZombie.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
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

    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    private float getAttackDamage(){
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    public boolean tryAttack(Entity target) {
        int i = this.attackTicksLeft;
        if (!this.hasStatusEffect(PvZCubed.FROZEN)) {
            if (this.firstAttack) {
                if (i <= 0) {
                    if (this.hasStatusEffect(StatusEffects.WEAKNESS)) {
                        this.attackTicksLeft = 20;
                        this.world.sendEntityStatus(this, (byte) 4);
                        float f = 184f;
                        boolean bl = target.damage(DamageSource.mob(this), f);
                        if (bl) {
                            this.applyDamageEffects(this, target);
                        }
                        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
                        this.firstAttack = false;
                        return bl;
                    } else {
                        this.attackTicksLeft = 20;
                        this.world.sendEntityStatus(this, (byte) 4);
                        float f = 180f;
                        boolean bl = target.damage(DamageSource.mob(this), f);
                        if (bl) {
                            this.applyDamageEffects(this, target);
                        }
                        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
                        this.firstAttack = false;
                        return bl;
                    }
                } else {
                    return false;
                }
            } else {
                if (i <= 0) {
                    this.attackTicksLeft = 20;
                    this.world.sendEntityStatus(this, (byte) 4);
                    float f = this.getAttackDamage();
                    boolean bl = target.damage(DamageSource.mob(this), f);
                    if (bl) {
                        this.applyDamageEffects(this, target);
                    }
                    return bl;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 10, this::predicate);

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

        if (entityData instanceof BerserkerEntity.ZombieData) {
            BerserkerEntity.ZombieData zombieData = (BerserkerEntity.ZombieData)entityData;

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

    public static boolean canBerserkerSpawn(EntityType<BerserkerEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 55;
    }

    protected void mobTick() {
        if (this.firstAttack) {
            this.world.sendEntityStatus(this, (byte) 13);
        }
        else {
            this.world.sendEntityStatus(this, (byte) 12);
        }
        super.mobTick();
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 13) {
            this.tackle = true;
        }
        else if (status == 12) {
            this.tackle = false;
        }
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
            return BerserkerEntity.this.owner != null && BerserkerEntity.this.owner.getTarget() != null && this.canTrack(BerserkerEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            BerserkerEntity.this.setTarget(BerserkerEntity.this.owner.getTarget());
            super.start();
        }
    }

}
