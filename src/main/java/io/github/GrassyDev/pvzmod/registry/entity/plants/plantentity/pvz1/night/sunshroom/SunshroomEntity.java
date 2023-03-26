package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
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
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.CONFIG;

public class SunshroomEntity extends PlantEntity implements IAnimatable {

	private static final TrackedData<Integer> SUN_SPEED;

	private String controllerName = "puffcontroller";
    public boolean isAsleep;
    public boolean isTired;
    public int sunProducingTime;



	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private Entity prevZombie;
	private boolean zombieSunCheck;
	private int raycastDelay = 20;

	public SunshroomEntity(EntityType<? extends SunshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

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
		SUN_SPEED = DataTracker.registerData(SunshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 113) {
			this.isTired = true;
		}
		else if (status == 112) {
			this.isTired = false;
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
        if (this.isTired) {
            event.getController().setAnimation(new AnimationBuilder().loop("sunshroom.asleep"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("sunshroom.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/
	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 50.0F));
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SUNSHROOM_SEED_PACKET);
				}
				this.kill();
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
				if (!this.world.isClient && this.isAlive() && this.zombieSunCheck && !this.isInsideWaterOrBubbleColumn() && !this.isAsleep) {
					this.playSound(PvZCubed.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
					double probability = this.random.nextDouble();
					if (probability <= CONFIG.nestedSun.sunshroomSunChance()) { // 45%
						this.dropItem(ModItems.SMALLSUN);
					} else if (probability <= CONFIG.nestedSun.sunshroomSun2ndChance()) { // 0.70 - 0.40 = 30%
						this.dropItem(ModItems.SUN);
					} else { // 25%
						this.dropItem(ModItems.LARGESUN);
					}
					this.sunProducingTime = (int) (CONFIG.nestedSun.sunshroomSec() * 20);
					this.zombieSunCheck = false;
					this.currentFuseTime = this.sunProducingTime;
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && !this.isAsleep) {
			if (--raycastDelay >= 0){
				this.produceSun();
				raycastDelay = 60;
			}
		}

		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}

	}

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

	protected void mobTick() {
		if ((this.world.getAmbientDarkness() >= 2 ||
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
				this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))
				&& !awakeSwitch) {
			this.world.sendEntityStatus(this, (byte) 112);
			this.initGoals();
			this.isAsleep = false;
			sleepSwitch = false;
			awakeSwitch = true;
		}
		else if (this.world.getAmbientDarkness() < 2 &&
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
				!this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))
				&& !sleepSwitch) {
			this.world.sendEntityStatus(this, (byte) 113);
			this.clearGoalsAndTasks();
			this.isAsleep = true;
			sleepSwitch = true;
			awakeSwitch = false;
		}
		super.mobTick();
	}

	protected void produceSun() {
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(15));
		List<GeneralPvZombieEntity> zombieList = this.world.getNonSpectatingEntities(GeneralPvZombieEntity.class, this.getBoundingBox().expand(15));
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
			} while (this.squaredDistanceTo(livingEntity) > 225);

			if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
				if (livingEntity.getY() < (this.getY() + 4) && livingEntity.getY() > (this.getY() - 4)) {
					if ((this.prevZombie == null || zombieList.get(0) != prevZombie) && !zombieList.isEmpty()) {
						prevZombie = zombieList.get(0);
						this.zombieSunCheck = true;
					}
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SUNSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createSunshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D)
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
		return 0.5F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZCubed.PLANTPLANTEDEVENT;
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
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}
}
