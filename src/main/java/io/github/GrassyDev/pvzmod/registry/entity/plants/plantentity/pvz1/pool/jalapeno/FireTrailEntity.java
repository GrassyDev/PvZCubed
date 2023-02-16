package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
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

import java.util.Iterator;
import java.util.List;

public class FireTrailEntity extends PathAwareEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "firetrailcontroller";

    public FireTrailEntity(EntityType<? extends FireTrailEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	static {
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
		event.getController().setAnimation(new AnimationBuilder().loop("firetrail.idle"));
		event.getController().setAnimationSpeed(1.25);
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}

	private void damageEntity() {
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 100);

			if (livingEntity instanceof Monster && !livingEntity.isWet()) {
				livingEntity.damage(DamageSource.thrownProjectile(this, this), 4);
				if (!livingEntity.isWet()) {
					if (!(livingEntity instanceof ZombieShieldEntity)) {
						livingEntity.removeStatusEffect(PvZCubed.FROZEN);
						livingEntity.removeStatusEffect(PvZCubed.ICE);
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						livingEntity.setOnFireFor(4);
					}
				}
			}
		}
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/
	private int tickDamage = 5;

	public void tick() {
		super.tick();
		if (this.getTarget() != null){
			this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.age <= 100){
			if (--tickDamage >= 0){
				this.damageEntity();
				tickDamage = 5;
			}
		}
		else {
			this.discard();
		}
		if (this.isWet()){
			playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH);
			this.discard();
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
            this.kill();
        }
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFireTrailAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return null	;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_FIRE_EXTINGUISH;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			this.clearStatusEffects();
			playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH);
			this.discard();
		}
		return false;
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}
}
