package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.entity.effect.StatusEffects;
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

public class HypnoBerserkerEntity extends GolemEntity implements IAnimatable {
private MobEntity owner;
public AnimationFactory factory = new AnimationFactory(this);
private String controllerName = "walkingcontroller";
    private int attackTicksLeft;
    public boolean firstAttack;
    public boolean tackle;

double tonguechance = this.random.nextDouble();

public HypnoBerserkerEntity(EntityType<? extends HypnoBerserkerEntity> entityType, World world) {
    super(entityType, world);
    this.ignoreCameraFrustum = true;
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

public HypnoBerserkerEntity(World world) {
    this(PvZEntity.HYPNOBERSERKER, world);
}

    protected void initGoals() {
    this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.add(8, new LookAroundGoal(this));
    this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
    this.initCustomGoals();
}

protected void initCustomGoals() {
    this.targetSelector.add(2, new HypnoBerserkerEntity.TrackOwnerTargetGoal(this));
    this.goalSelector.add(1, new HypnoBerserkerAttackGoal(this, 1.0D, true));
    this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
        return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
                !(livingEntity instanceof HypnoFlagzombieEntity);
    }));
}

public static DefaultAttributeContainer.Builder createHypnoBerserkerAttributes() {
    return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 180D);
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

class TrackOwnerTargetGoal extends TrackTargetGoal {

	private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

    public TrackOwnerTargetGoal(PathAwareEntity mob) {
        super(mob, false);
    }

    public boolean canStart() {
        return HypnoBerserkerEntity.this.owner != null && HypnoBerserkerEntity.this.owner.getTarget() != null && this.canTrack(HypnoBerserkerEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
    }

    public void start() {
        HypnoBerserkerEntity.this.setTarget(HypnoBerserkerEntity.this.owner.getTarget());
        super.start();
    }
}


}
