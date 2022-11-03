package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday.HypnoBrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.buckethead.modernday.HypnoBucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday.HypnoConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.PvZombieAttackGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HypnoFlagzombieEntity extends HypnoSummonerEntity implements IAnimatable {
private MobEntity owner;
public AnimationFactory factory = new AnimationFactory(this);
private String controllerName = "walkingcontroller";
    private boolean spawning;

double tonguechance = this.random.nextDouble();

public HypnoFlagzombieEntity(EntityType<? extends HypnoFlagzombieEntity> entityType, World world) {
    super(entityType, world);
    this.ignoreCameraFrustum = true;
    this.spawning = true;
}

private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
    if (tonguechance <= 0.5) {
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flagzombie.walking", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flagzombie.idle", true));
        }
    }
    else {
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flagzombie.walking2", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flagzombie.idle2", true));
        }
    }
    return PlayState.CONTINUE;
}

public HypnoFlagzombieEntity(World world) {
    this(PvZEntity.HYPNOFLAGZOMBIE, world);
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

    @Override
    public SoundEvent getCelebratingSound() {
        return null;
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new HypnoFlagzombieEntity.summonZombieGoal());
    this.targetSelector.add(2, new HypnoFlagzombieEntity.TrackOwnerTargetGoal(this));
    this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
    this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
        return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                !(livingEntity instanceof HypnoFlagzombieEntity);
    }));
}

public static DefaultAttributeContainer.Builder createHypnoFlagzombieAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 27D);
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

protected SoundEvent getStepSound() {
    return SoundEvents.ENTITY_ZOMBIE_STEP;
}

protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(this.getStepSound(), 0.15F, 1.0F);
}

public EntityGroup getGroup() {
    return EntityGroup.UNDEAD;
}

public void updatePassengerPosition(Entity passenger) {
    super.updatePassengerPosition(passenger);
    float f = MathHelper.sin(this.bodyYaw * 0.017453292F);
    float g = MathHelper.cos(this.bodyYaw * 0.017453292F);
    float h = 0.1F;
    float i = 0.0F;
    passenger.updatePosition(this.getX() + (double)(0.1F * f), this.getBodyY(0.5D) + passenger.getHeightOffset() + 0.0D, this.getZ() - (double)(0.1F * g));
    if (passenger instanceof LivingEntity) {
        ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
    }

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
public boolean canSpawn(WorldView worldreader) {
    return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
}

    @Override
    protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {

	private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

    public TrackOwnerTargetGoal(PathAwareEntity mob) {
        super(mob, false);
    }

    public boolean canStart() {
        return HypnoFlagzombieEntity.this.owner != null && HypnoFlagzombieEntity.this.owner.getTarget() != null && this.canTrack(HypnoFlagzombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
    }

    public void start() {
        HypnoFlagzombieEntity.this.setTarget(HypnoFlagzombieEntity.this.owner.getTarget());
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
            if (HypnoFlagzombieEntity.this.spawning) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = HypnoFlagzombieEntity.this.world.getTargets(HypnoScreendoorEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = HypnoFlagzombieEntity.this.world.getTargets(HypnoConeheadEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = HypnoFlagzombieEntity.this.world.getTargets(HypnoBucketheadEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = HypnoFlagzombieEntity.this.world.getTargets(HypnoBrowncoatEntity.class, this.closeZombiePredicate, HypnoFlagzombieEntity.this, HypnoFlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > b &&
                            HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > p &&
                            HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > d &&
                            HypnoFlagzombieEntity.this.random.nextInt(8) + 1 > t ;
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
            ServerWorld serverWorld = (ServerWorld)HypnoFlagzombieEntity.this.world;

            for(int b = 0; b < 1; ++b) { // 1 hypno screendoor
                BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(-2 + HypnoFlagzombieEntity.this.random.nextInt(10), 0.1, -2 + HypnoFlagzombieEntity.this.random.nextInt(10));
                HypnoScreendoorEntity hypnoScreendoorEntity = (HypnoScreendoorEntity)PvZEntity.HYPNOSCREENDOOR.create(HypnoFlagzombieEntity.this.world);
                hypnoScreendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoScreendoorEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound) null);
                hypnoScreendoorEntity.setOwner(HypnoFlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoScreendoorEntity);
            }
            for(int p = 0; p < 1; ++p) { // 1 hypno conehead
                BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(0, 0.1, +1);
                HypnoConeheadEntity hypnoConeheadEntity = (HypnoConeheadEntity)PvZEntity.HYPNOCONEHEAD.create(HypnoFlagzombieEntity.this.world);
                hypnoConeheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoConeheadEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoConeheadEntity.setOwner(HypnoFlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoConeheadEntity);
            }
            for(int d = 0; d < 1; ++d) { // 1 hypno buckethead
                BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(+1, 0.1, 0);
                HypnoBucketheadEntity hypnoBucketheadEntity = (HypnoBucketheadEntity)PvZEntity.HYPNOBUCKETHEAD.create(HypnoFlagzombieEntity.this.world);
                hypnoBucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBucketheadEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoBucketheadEntity.setOwner(HypnoFlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBucketheadEntity);
            }
            for(int t = 0; t < 1; ++t) { // 1 hypno browncoat
                BlockPos blockPos = HypnoFlagzombieEntity.this.getBlockPos().add(+0, 0.1, -1);
                HypnoBrowncoatEntity hypnoBrowncoatEntity = (HypnoBrowncoatEntity)PvZEntity.HYPNOBROWNCOAT.create(HypnoFlagzombieEntity.this.world);
                hypnoBrowncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                hypnoBrowncoatEntity.initialize(serverWorld, HypnoFlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                hypnoBrowncoatEntity.setOwner(HypnoFlagzombieEntity.this);
                serverWorld.spawnEntityAndPassengers(hypnoBrowncoatEntity);
                HypnoFlagzombieEntity.this.spawning = false;
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
