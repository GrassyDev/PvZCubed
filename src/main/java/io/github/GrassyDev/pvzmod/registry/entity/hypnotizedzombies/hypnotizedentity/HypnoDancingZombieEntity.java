package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.HypnoSummonerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HypnoDancingZombieEntity extends HypnoSummonerEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";
    private boolean dancing;

    public HypnoDancingZombieEntity(EntityType<? extends HypnoDancingZombieEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
        this.dancing = true;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dancingzombie.dancewalk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dancingzombie.dancing", true));
        }
        return PlayState.CONTINUE;
    }

    public HypnoDancingZombieEntity(World world) {
        this(PvZEntity.HYPNODANCINGZOMBIE, world);
    }

        protected void initGoals() {
			this.goalSelector.add(1, new HypnoSummonerEntity.AttackGoal());
            this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.add(8, new LookAroundGoal(this));
            this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
            this.initCustomGoals();
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new HypnoDancingZombieEntity.summonZombieGoal());
        this.targetSelector.add(2, new HypnoDancingZombieEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new HypnoDancingZombieAttackGoal(this, 1.0D, true));
        this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                    !(livingEntity instanceof HypnoFlagzombieEntity);
        }));
    }

    public static DefaultAttributeContainer.Builder createHypnoDancingZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
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

    @Override
    public SoundEvent getCelebratingSound() {
        return null;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    @Override
    public void onDeath(DamageSource source){

    }

    protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient && this.isSpellcasting() && this.dancing) {
            this.world.addParticle(ParticleTypes.FLASH, this.getX(), this.getY() + 1D, this.getZ(), 0, 0, 0);
        }

    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return HypnoDancingZombieEntity.this.owner != null && HypnoDancingZombieEntity.this.owner.getTarget() != null && this.canTrack(HypnoDancingZombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            HypnoDancingZombieEntity.this.setTarget(HypnoDancingZombieEntity.this.owner.getTarget());
            super.start();
        }
    }

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

        private summonZombieGoal() {
            super();
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (HypnoDancingZombieEntity.this.dancing) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = HypnoDancingZombieEntity.this.world.getTargets(HypnoBackupDancerEntity.class, this.closeZombiePredicate, HypnoDancingZombieEntity.this, HypnoDancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = HypnoDancingZombieEntity.this.world.getTargets(HypnoBackupDancerEntity.class, this.closeZombiePredicate, HypnoDancingZombieEntity.this, HypnoDancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = HypnoDancingZombieEntity.this.world.getTargets(HypnoBackupDancerEntity.class, this.closeZombiePredicate, HypnoDancingZombieEntity.this, HypnoDancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = HypnoDancingZombieEntity.this.world.getTargets(HypnoBackupDancerEntity.class, this.closeZombiePredicate, HypnoDancingZombieEntity.this, HypnoDancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return HypnoDancingZombieEntity.this.random.nextInt(8) + 1 > b &&
                            HypnoDancingZombieEntity.this.random.nextInt(8) + 1 > p &&
                            HypnoDancingZombieEntity.this.random.nextInt(8) + 1 > d &&
                            HypnoDancingZombieEntity.this.random.nextInt(8) + 1 > t ;
                }
            } else {
                return false;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)HypnoDancingZombieEntity.this.world;

            for(int b = 0; b < 1; ++b) { // 1 backup
                BlockPos blockPos = HypnoDancingZombieEntity.this.getBlockPos().add(-1, 0.1, 0);
                HypnoBackupDancerEntity hypnoBackupDancerEntity = (HypnoBackupDancerEntity)PvZEntity.HYPNOBACKUPDANCER.create(HypnoDancingZombieEntity.this.world);
                hypnoBackupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBackupDancerEntity.initialize(serverWorld, HypnoDancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound) null);
                hypnoBackupDancerEntity.setOwner(HypnoDancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBackupDancerEntity);
            }
            for(int p = 0; p < 1; ++p) { // 1 backup
                BlockPos blockPos = HypnoDancingZombieEntity.this.getBlockPos().add(0, 0.1, +1);
                HypnoBackupDancerEntity hypnoBackupDancerEntity = (HypnoBackupDancerEntity)PvZEntity.HYPNOBACKUPDANCER.create(HypnoDancingZombieEntity.this.world);
                hypnoBackupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBackupDancerEntity.initialize(serverWorld, HypnoDancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoBackupDancerEntity.setOwner(HypnoDancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBackupDancerEntity);
            }
            for(int d = 0; d < 1; ++d) { // 1 backup
                BlockPos blockPos = HypnoDancingZombieEntity.this.getBlockPos().add(+1, 0.1, 0);
                HypnoBackupDancerEntity hypnoBackupDancerEntity = (HypnoBackupDancerEntity)PvZEntity.HYPNOBACKUPDANCER.create(HypnoDancingZombieEntity.this.world);
                hypnoBackupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBackupDancerEntity.initialize(serverWorld, HypnoDancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoBackupDancerEntity.setOwner(HypnoDancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBackupDancerEntity);
            }
            for(int t = 0; t < 1; ++t) { // 1 backup
                BlockPos blockPos = HypnoDancingZombieEntity.this.getBlockPos().add(+0, 0.1, -1);
                HypnoBackupDancerEntity hypnoBackupDancerEntity = (HypnoBackupDancerEntity) PvZEntity.HYPNOBACKUPDANCER.create(HypnoDancingZombieEntity.this.world);
                hypnoBackupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBackupDancerEntity.initialize(serverWorld, HypnoDancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoBackupDancerEntity.setOwner(HypnoDancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBackupDancerEntity);
                HypnoDancingZombieEntity.this.dancing = false;
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.ZOMBIEDANCINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
