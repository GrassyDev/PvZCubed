package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basketballcarrier;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.ShootingBasketballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BullyVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class BasketballCarrierEntity extends BullyEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";

	private int launchAnimation;
	public boolean inLaunchAnimation;

	public BasketballCarrierEntity(EntityType<? extends BasketballCarrierEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 104) {
			this.inLaunchAnimation = true;
		}
		else if (status == 103) {
			this.inLaunchAnimation = false;
		}
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(BasketballCarrierEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public BullyVariants getVariant() {
		return BullyVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(BullyVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
		Entity entity = this.getFirstPassenger();
		if (this.isInsideWaterOrBubbleColumn()) {
			if (inLaunchAnimation) {
				event.getController().setAnimation(new AnimationBuilder().loop("bully.duckythrow"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				}
				else {
					event.getController().setAnimationSpeed(1);
				}
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("bully.ducky"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(1);
				}
				else {
					event.getController().setAnimationSpeed(2);
				}
			}
		} else {
			if (inLaunchAnimation) {
				event.getController().setAnimation(new AnimationBuilder().loop("bully.throw"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
			else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity) {
					event.getController().setAnimation(new AnimationBuilder().loop("bully.push"));
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.25);
					} else {
						event.getController().setAnimationSpeed(0.5);
					}
				}
				else {
					if (inLaunchAnimation) {
						event.getController().setAnimation(new AnimationBuilder().loop("bully.throw"));
						if (this.isIced) {
							event.getController().setAnimationSpeed(0.5);
						} else {
							event.getController().setAnimationSpeed(1);
						}
					} else {
						event.getController().setAnimation(new AnimationBuilder().loop("bully.walk"));
						if (this.isFrozen || this.isStunned) {
							event.getController().setAnimationSpeed(0);
						} else if (this.isIced) {
							event.getController().setAnimationSpeed(1);
						} else {
							event.getController().setAnimationSpeed(2);
						}
					}
				}
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("bully.idle"));
				if (this.isFrozen || this.isStunned) {
					event.getController().setAnimationSpeed(0);
				} else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		super.initGoals();
    }

    protected void initCustomGoals() {
		super.initCustomGoals();
    }

	protected void initHypnoGoals(){
		super.initHypnoGoals();
	}

	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.inLaunchAnimation && this.getTarget() != null) {
			return super.tryAttack(this.getTarget());
		}
		else {
			return false;
		}
	}

	//Launch Basket
	public void tryLaunch(Entity target) {
		ShootingBasketballEntity basketballEntity = new ShootingBasketballEntity(PvZEntity.BASKETBALLPROJ, this.world);
		if (launchAnimation == 29 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			if (this.getTarget() instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.hasVehicle()){
				target = zombiePropEntity.getVehicle();
			}
			if (this.getTarget() != null) {
				assert target != null;
				Vec3d targetPos = target.getPos();
				double d = this.squaredDistanceTo(targetPos);
				float df = (float) d;
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				Vec3d projPos = new Vec3d(this.getX(), this.getY() + 2.3D, this.getZ());
				Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, targetPos, 5);
				basketballEntity.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(), 1F, 0F);
				basketballEntity.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
				basketballEntity.setOwner(this);
				basketballEntity.getTarget((LivingEntity) target);
			}
			else {
				basketballEntity.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 1F, 0F);
			}
			if (this.getHypno()) {
				basketballEntity.isHypno = true;
			}

			basketballEntity.setOwner(this);
			this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
			this.world.spawnEntity(basketballEntity);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
	}

	protected void mobTick() {
		super.mobTick();
		var zombieObstacleEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieObstacleEntity)
				.map(e -> (ZombieObstacleEntity) e)
				.findFirst();
		double random = Math.random();
		if (this.isInsideWaterOrBubbleColumn() && zombieObstacleEntity.isPresent()){
			zombieObstacleEntity.get().stopRiding();
		}
		if (zombieObstacleEntity.isEmpty() && this.CollidesWithObstacle(1f) != null && this.CollidesWithObstacle(1f).getType().equals(PvZEntity.BASKETBALLBIN) && !this.CollidesWithObstacle(1f).hasVehicle() && !this.CollidesWithObstacle(1f).beingEaten && !this.isInsideWaterOrBubbleColumn()){
			this.CollidesWithObstacle(1f).startRiding(this, true);
		}
		if (random <= 0.0075 && zombieObstacleEntity.isPresent() && getTarget() != null && this.squaredDistanceTo(this.getTarget()) <= 225 && !this.inLaunchAnimation) {
			this.launchAnimation = 80 * animationMultiplier;
			this.inLaunchAnimation = true;
			this.world.sendEntityStatus(this, (byte) 104);
		}
		if (this.launchAnimation > 0) {
			this.getNavigation().stop();
			--launchAnimation;
			tryLaunch(getTarget());
			this.inLaunchAnimation = true;
			this.world.sendEntityStatus(this, (byte) 104);
		}
		else {
			this.inLaunchAnimation = false;
			this.world.sendEntityStatus(this, (byte) 103);
		}
	}

	@Override
	public void updatePassengerPosition(Entity passenger) {
		super.updatePassengerPosition(passenger);
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		itemStack = ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBasketballCarrierAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.basketballH());
    }

	public Vec3d fire_velocity;

	public Vec3d solve_ballistic_arc_lateral(Vec3d proj_pos, float lateral_speed, Vec3d target_pos, float max_height) {

		Vec3d diff = target_pos.subtract(proj_pos);
		Vec3d diffXZ = new Vec3d(diff.x, 0f, diff.z);
		float lateralDist = (float) diffXZ.length();

		if (lateralDist == 0)
			return fire_velocity = Vec3d.ZERO;

		float time = lateralDist / lateral_speed;

		fire_velocity = diffXZ.normalize().multiply(lateral_speed);

		// System of equations. Hit max_height at t=.5*time. Hit target at t=time.
		//
		// peak = y0 + vertical_speed*halfTime + .5*gravity*halfTime^2
		// end = y0 + vertical_speed*time + .5*gravity*time^s
		// Wolfram Alpha: solve b = a + .5*v*t + .5*g*(.5*t)^2, c = a + vt + .5*g*t^2 for g, v
		float a = (float) proj_pos.y;       // initial
		float b = max_height;       // peak
		float c = (float) target_pos.y;     // final

		return fire_velocity.subtract(0, -(3*a - 4*b + c) / time, 0);
	}
}
