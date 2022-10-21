package io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class NightGraveEntity extends SpellcastingIllagerEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";

    double tiltchance = this.random.nextDouble();

    public NightGraveEntity(EntityType<NightGraveEntity> entityType, World world) {
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

    public NightGraveEntity(World world) {
        this(PvZEntity.NIGHTGRAVESTONE, world);
    }

    protected void initGoals() {
        this.targetSelector.add(1, new TargetGoal(this, PlayerEntity.class, false, false));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new NightGraveEntity.summonZombieGoal());
        this.targetSelector.add(2, new NightGraveEntity.TrackOwnerTargetGoal(this));
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    @Override
    public void onDeath(DamageSource source){

    }

    public static DefaultAttributeContainer.Builder createNightGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150D);
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

    public static boolean canNightGraveSpawn(EntityType<NightGraveEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 0;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

        private summonZombieGoal() {
            super();
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int a = NightGraveEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int h = NightGraveEntity.this.world.getTargets(ConeheadEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int b = NightGraveEntity.this.world.getTargets(NewspaperEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int c = NightGraveEntity.this.world.getTargets(ScreendoorEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int u = NightGraveEntity.this.world.getTargets(FootballEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int p = NightGraveEntity.this.world.getTargets(BerserkerEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                int f = NightGraveEntity.this.world.getTargets(DancingZombieEntity.class, this.closeZombiePredicate, NightGraveEntity.this, NightGraveEntity.this.getBoundingBox().expand(16.0D)).size();
                return NightGraveEntity.this.random.nextInt(8) + 1 > a &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > h &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > b &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > c &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > u &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > p &&
                        NightGraveEntity.this.random.nextInt(8) + 1 > f  ;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) NightGraveEntity.this.world;
            double probability = random.nextDouble();
            double probability2 = random.nextDouble();
            double probability3 = random.nextDouble();
            double probability4 = random.nextDouble();
            double probability5 = random.nextDouble();
            double probability6 = random.nextDouble();


            for(int a = 0; a < 2; ++a) { // 100% x2 Browncoat
                BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(NightGraveEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(NightGraveEntity.this);
                serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
            if (probability <= 0.5) { // 50% x2 Conehead
                for (int h = 0; h < 1; ++h) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    ConeheadEntity coneheadEntity = (ConeheadEntity) PvZEntity.CONEHEAD.create(NightGraveEntity.this.world);
                    coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    coneheadEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    coneheadEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(coneheadEntity);
                }
            }
            if (probability2 <= 0.5) {  // 50% x2 Newspaper
                for (int b = 0; b < 2; ++b) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.world);
                    newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    newspaperEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                    newspaperEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(newspaperEntity);
                }
            }
            if (probability3 <= 0.5) { // 50% x1 Screendoor
                for(int c = 0; c < 1; ++c) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    ScreendoorEntity screendoorEntity = (ScreendoorEntity) PvZEntity.SCREEENDOOR.create(NightGraveEntity.this.world);
                    screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    screendoorEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    screendoorEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(screendoorEntity);
                }
            }
            if (probability4 <= 0.25) { // 25% x1 Football
                for(int u = 0; u < 1; ++u) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    FootballEntity footballEntity = (FootballEntity) PvZEntity.FOOTBALL.create(NightGraveEntity.this.world);
                    footballEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    footballEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    footballEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(footballEntity);
                }
            }
            if (probability5 <= 0.1) { // 10% x1 Berserker
                for(int p = 0; p < 1; ++p) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    BerserkerEntity berserkerEntity = (BerserkerEntity) PvZEntity.BERSERKER.create(NightGraveEntity.this.world);
                    berserkerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    berserkerEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    berserkerEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(berserkerEntity);
                }
            }
            if (probability6 <= 0.35) { // 35% x1 Dancing Zombie
                for(int f = 0; f < 1; ++f) {
                    BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(-2 + NightGraveEntity.this.random.nextInt(5), 0.1, -2 + NightGraveEntity.this.random.nextInt(5));
                    DancingZombieEntity dancingZombieEntity = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(NightGraveEntity.this.world);
                    dancingZombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    dancingZombieEntity.initialize(serverWorld, NightGraveEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                    dancingZombieEntity.setOwner(NightGraveEntity.this);
                    serverWorld.spawnEntityAndPassengers(dancingZombieEntity);
                }
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return NightGraveEntity.this.owner != null && NightGraveEntity.this.owner.getTarget() != null && this.canTrack(NightGraveEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            NightGraveEntity.this.setTarget(NightGraveEntity.this.owner.getTarget());
            super.start();
        }
    }

}
