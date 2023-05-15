package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
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

public class MagichatEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "hatcontroller";

    public MagichatEntity(EntityType<? extends MagichatEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 116){
			for(int i = 0; i < 128; ++i) {
				double random = Math.random();
				this.world.addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + ((this.random.range(-1, 1)) * random), this.getY() + + ((this.random.range(0, 2)) * random), this.getZ() + ((this.random.range(-1, 1)) * random), 0, 0, 0);
			}
		}
		if (status == 117){
			for(int i = 0; i < 32; ++i) {
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(0, 3)), this.getZ() + (this.random.range(-1, 1)), 0, 0.2, 0);
			}
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		this.playSound(PvZSounds.MAGICHATAPPEAREVENT, 0.5f, 1);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
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
		event.getController().setAnimation(new AnimationBuilder().loop("magicshroom.magichat"));
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				this.kill();
			}
		}
	}

	/** /~*~//~**TICKING**~//~*~/ **/


	public void tick() {
		super.tick();
		if (this.age < 3){
			this.world.sendEntityStatus(this, (byte) 116);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/


	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.TULIMPETER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMagicHatAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D);
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
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
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
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	public void onDeath(DamageSource source) {
		double random = Math.random();
		if (this.world instanceof ServerWorld serverWorld) {
			if (random <= 0.33) {
				Vec3d blockPos = Vec3d.ofCenter(this.getBlockPos());
				BrowncoatEntity zombie = (BrowncoatEntity) PvZEntity.BROWNCOATHYPNO.create(world);
				zombie.refreshPositionAndAngles(blockPos.getX(), this.getY(), blockPos.getZ(), 0, 0);
				zombie.initialize(serverWorld, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				((ServerWorld) this.world).spawnEntityAndPassengers(zombie);
			}
			else if (random <= 0.66) {
				Vec3d blockPos = Vec3d.ofCenter(this.getBlockPos());
				BrowncoatEntity zombie = (BrowncoatEntity) PvZEntity.CONEHEADHYPNO.create(world);
				zombie.refreshPositionAndAngles(blockPos.getX(), this.getY(), blockPos.getZ(), 0, 0);
				zombie.initialize(serverWorld, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				zombie.createConeheadProp();
				((ServerWorld) this.world).spawnEntityAndPassengers(zombie);
			}
			else {
				Vec3d blockPos = Vec3d.ofCenter(this.getBlockPos());
				BrowncoatEntity zombie = (BrowncoatEntity) PvZEntity.BUCKETHEADHYPNO.create(world);
				zombie.refreshPositionAndAngles(blockPos.getX(), this.getY(), blockPos.getZ(), 0, 0);
				zombie.initialize(serverWorld, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				zombie.createConeheadProp();
				((ServerWorld) this.world).spawnEntityAndPassengers(zombie);
			}
		}
		this.playSound(PvZSounds.MAGICHATZOMBIEEVENT, 3, 1);
		this.world.sendEntityStatus(this, (byte) 117);
		super.onDeath(source);
		this.discard();
	}
}
