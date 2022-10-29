package io.github.GrassyDev.pvzmod.registry.plants.plantentity.potatomine;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import java.util.Optional;
import java.util.Random;

public class UnarmedPotatomineEntity extends GolemEntity implements IAnimatable {

    public AnimationFactory factory = new AnimationFactory(this);
    public int potatoPreparingTime;
	private String controllerName = "potatocontroller";

    public UnarmedPotatomineEntity(EntityType<? extends UnarmedPotatomineEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.potatoPreparingTime = 600;
    }

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		RandomGenerator randomGenerator = this.getRandom();
		BlockState blockState = this.getLandingBlockState();
		if (status == 8) {
			for(int i = 0; i < 30; ++i) {
				double d = this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				double e = this.getY();
				double f = this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
	}

	static {
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

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

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("potatomine.unarmed", true));
		return PlayState.CONTINUE;
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.isAlive() && --this.potatoPreparingTime <= 0) {
			((LivingEntity) this).addStatusEffect((new StatusEffectInstance(PvZCubed.HYPNOTIZED, 999, 1))); // applies a status effect
		}
	}

	public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.damage(DamageSource.GENERIC, 9999);
        }
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.SMALLSUN)) {
			this.potatoPreparingTime = 0;
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.CONSUME;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createUnarmedPotatomineAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1.5D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 180);
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
		return null;
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


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			this.clearStatusEffects();
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.damage(DamageSource.GENERIC, 9999);
		}
		this.playBlockFallSound();
		return true;
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

			if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
				this.playSound(PvZCubed.ENTITYRISINGEVENT, 1.5F, 1.0F);
				PotatomineEntity potatomineEntity = (PotatomineEntity) PvZEntity.POTATOMINE.create(world);
				potatomineEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				potatomineEntity.initialize(serverWorld, world.getLocalDifficulty(potatomineEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				potatomineEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					potatomineEntity.setCustomName(this.getCustomName());
					potatomineEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				this.world.sendEntityStatus(this, (byte) 8);

				potatomineEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(potatomineEntity);
				this.remove(RemovalReason.DISCARDED);
			}

			return true;
		}
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canUnarmedPotatomineSpawn(EntityType<UnarmedPotatomineEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 0 && pos.getY() < 65;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }
}
