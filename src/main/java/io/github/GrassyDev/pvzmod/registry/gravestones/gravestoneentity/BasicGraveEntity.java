package io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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

import java.util.Random;

public class BasicGraveEntity extends SpellcastingIllagerEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";

    double tiltchance = this.random.nextDouble();

    public BasicGraveEntity(EntityType<BasicGraveEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 25;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (tiltchance <= 0.5) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gravestone.idle", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gravestone.idle2", true));
        }
        return PlayState.CONTINUE;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    public BasicGraveEntity(World world) {
        this(PvZEntity.BASICGRAVESTONE, world);
    }

    protected void initGoals() {
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, false, false));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new BasicGraveEntity.summonZombieGoal());
        this.targetSelector.add(2, new BasicGraveEntity.TrackOwnerTargetGoal(this));
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    @Override
    public void onDeath(DamageSource source){

    }

    public static DefaultAttributeContainer.Builder createBasicGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 140D);
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
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
            return true;
        }
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_BASALT_HIT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
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

    public static boolean canBasicGraveSpawn(EntityType<BasicGraveEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 0;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

    class summonZombieGoal extends BasicGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

        private summonZombieGoal() {
            super();
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int b = BasicGraveEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int c = BasicGraveEntity.this.world.getTargets(ConeheadEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int u = BasicGraveEntity.this.world.getTargets(BucketheadEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int p = BasicGraveEntity.this.world.getTargets(PoleVaultingEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int f = BasicGraveEntity.this.world.getTargets(FlagzombieEntity.class, this.closeZombiePredicate, BasicGraveEntity.this, BasicGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                return BasicGraveEntity.this.random.nextInt(8) + 1 > b &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > c &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > u &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > p &&
                        BasicGraveEntity.this.random.nextInt(8) + 1 > f  ;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)BasicGraveEntity.this.world;
            double probability = random.nextDouble();
            double probability2 = random.nextDouble();
            double probability3 = random.nextDouble();
            double probability4 = random.nextDouble();
            double probability5 = random.nextDouble();

            for(int b = 0; b < 2; ++b) { // 100% x2 Browncoat
                BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(BasicGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.5) { // 50% x2 Conehead
                for(int c = 0; c < 2; ++c) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    ConeheadEntity coneheadEntity = (ConeheadEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    coneheadEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
            if (probability2 <= 0.3) { // 30% x1 Buckethead
                for(int u = 0; u < 1; ++u) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    BucketheadEntity bucketheadEntity = (BucketheadEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.world);
                    bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    bucketheadEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(bucketheadEntity);
                }
            }
            if (probability3 <= 0.3) { // 30% x1 Pole Vaulting Zombie
                for(int p = 0; p < 1; ++p) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
                    poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    poleVaultingEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
                }
            }
            if (probability5 <= 0.2) { // 20% x1 Flag Zombie
                for(int f = 0; f < 1; ++f) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    FlagzombieEntity flagzombieEntity = (FlagzombieEntity) PvZEntity.FLAGZOMBIE.create(BasicGraveEntity.this.world);
                    flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    flagzombieEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    flagzombieEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(flagzombieEntity);
                }
            }
            if (probability4 <= 0.15) { // 15% x1 Pole Vaulting Zombie
                for(int p = 0; p < 1; ++p) {
                    BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(-2 + BasicGraveEntity.this.random.nextInt(5), 0.1, -2 + BasicGraveEntity.this.random.nextInt(5));
                    PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.world);
                    poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    poleVaultingEntity.setOwner(BasicGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
                }
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected BasicGraveEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return BasicGraveEntity.this.owner != null && BasicGraveEntity.this.owner.getTarget() != null && this.canTrack(BasicGraveEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            BasicGraveEntity.this.setTarget(BasicGraveEntity.this.owner.getTarget());
            super.start();
        }
    }

}
