package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiegrave;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
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

public class ZombieGraveEntity extends ZombieObstacleEntity implements IAnimatable {

	private static final TrackedData<Integer> SUN_SPEED;

	private String controllerName = "walkingcontroller";

	public int sunProducingTime;

	private MobEntity owner;

	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private boolean zombieSunCheck;

    public ZombieGraveEntity(EntityType<ZombieGraveEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 25;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SUN_SPEED, -1);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("Fuse", 99)) {
			this.sunProducingTime = tag.getShort("Fuse");
		}
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putShort("Fuse", (short)this.sunProducingTime);
	}

	static {
		SUN_SPEED = DataTracker.registerData(ZombieGraveEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
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
		if (beingEaten){
			event.getController().setAnimation(new AnimationBuilder().loop("obstacle.eating"));
		}
        else if (tiltchance <= 0.5) {
            event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle2"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, GardenEntity.class, 50.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, SunflowerEntity.class, 50.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, TwinSunflowerEntity.class, 50.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, SunshroomEntity.class, 50.0F));
    }


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				this.discard();
			}
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	private int currentFuseTime;

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(SUN_SPEED, fuseSpeed);
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(SUN_SPEED);
	}

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}

		if (this.isAlive()) {
			this.setFuseSpeed(1);

			int i = this.getFuseSpeed();

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.sunProducingTime) {
				if (!this.world.isClient && this.isAlive() && this.zombieSunCheck && !this.isInsideWaterOrBubbleColumn()){
					this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) - 0.125f);
					this.dropItem(ModItems.BRAIN);
					this.sunProducingTime = (int) (PVZCONFIG.nestedSun.zombiegraveSec() * 20);
					this.zombieSunCheck = false;
					this.currentFuseTime = this.sunProducingTime;
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn()) {
			this.zombieSunCheck = true;
		}
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createZombieGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.zombieGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}



	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ZOMBIEGRAVESPAWN.getDefaultStack();
	}

}
