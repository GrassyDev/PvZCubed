package net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.registry.ModBlocks;
import net.fabricmc.example.registry.PvZEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class HypnoNewspaperEntity extends GolemEntity implements IAnimatable {
private MobEntity owner;
public AnimationFactory factory = new AnimationFactory(this);
private String controllerName = "walkingcontroller";

double tonguechance = this.random.nextDouble();

public HypnoNewspaperEntity(EntityType<? extends HypnoNewspaperEntity> entityType, World world) {
    super(entityType, world);
    this.ignoreCameraFrustum = true;
}

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("newspaper.walking", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("newspaper.idle", true));
        }
        return PlayState.CONTINUE;
    }

public HypnoNewspaperEntity(World world) {
    this(PvZEntity.HYPNONEWSPAPER, world);
}

    protected void initGoals() {
    this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.add(8, new LookAroundGoal(this));
    this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
    this.initCustomGoals();
}

protected void initCustomGoals() {
    this.targetSelector.add(2, new HypnoNewspaperEntity.TrackOwnerTargetGoal(this));
    this.goalSelector.add(1, new HypnoNewspaperAttackGoal(this, 1.0D, true));
    this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
        return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                !(livingEntity instanceof HypnoFlagzombieEntity);
    }));
}

public static DefaultAttributeContainer.Builder createHypnoNewspaperAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 42D);
}

public MobEntity getOwner() {
    return this.owner;
}

public void setOwner(MobEntity owner) {
    this.owner = owner;
}

    protected SoundEvent getAmbientSound() {
        return ExampleMod.ZOMBIEMOANEVENT;
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
    AnimationController controller = new AnimationController(this, controllerName, 10, this::predicate);

    data.addAnimationController(controller);
}

@Override
public AnimationFactory getFactory()
{
    return this.factory;
}

@Override
public boolean canSpawn(WorldView worldreader) {
    return worldreader.intersectsEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
}

class TrackOwnerTargetGoal extends TrackTargetGoal {
    private final TargetPredicate TRACK_OWNER_PREDICATE = (new TargetPredicate()).includeHidden().ignoreDistanceScalingFactor();

    public TrackOwnerTargetGoal(PathAwareEntity mob) {
        super(mob, false);
    }

    public boolean canStart() {
        return HypnoNewspaperEntity.this.owner != null && HypnoNewspaperEntity.this.owner.getTarget() != null && this.canTrack(HypnoNewspaperEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
    }

    public void start() {
        HypnoNewspaperEntity.this.setTarget(HypnoNewspaperEntity.this.owner.getTarget());
        super.start();
    }
}


}
