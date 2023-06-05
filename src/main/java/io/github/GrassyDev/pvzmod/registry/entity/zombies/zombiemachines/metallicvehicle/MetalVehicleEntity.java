package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zomboni.ZomboniEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MetalVehicleEntity extends ZombieVehicleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public MetalVehicleEntity(EntityType<? extends MetalVehicleEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 106) {
			for(int i = 0; i < 170; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.FLAME, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1));
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
	}

	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		GeneralPvZombieEntity zombiePassenger = null;
		for (Entity entity : this.getPassengerList()){
			if (entity instanceof GeneralPvZombieEntity){
				zombiePassenger = (GeneralPvZombieEntity) entity;
			}
		}
		for (float x = 0; x <= 1; ++x){
			if (this.CollidesWithPlant(x) != null && !this.getPassengerList().contains(this.CollidesWithPlant(x)) && this.isAlive()){
				if (this.CollidesWithPlant(x) instanceof SpikerockEntity) {
					this.CollidesWithPlant(x).damage(DamageSource.thrownProjectile(this, this), 90);
					this.kill();
				}
				else if (this.CollidesWithPlant(x) instanceof SpikeweedEntity) {
					this.CollidesWithPlant(x).kill();
					this.kill();
				}else if (this.CollidesWithPlant(x) != null) {
					this.CollidesWithPlant(x).kill();
				}
			}
		}
		Vec3d vec3d = new Vec3d((double)1.25, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PvZProjectileEntity> list = world.getNonSpectatingEntities(PvZProjectileEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		for (PvZProjectileEntity projectileEntity : list){
			projectileEntity.moreEntities.add(this);
			projectileEntity.hitEntities();
		}
	}

	@Override
	public void updatePassengerPosition(Entity passenger) {
		if (passenger instanceof ZombieVehicleEntity) {
			float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
			float f = 0F;

			Vec3d vec3d = new Vec3d((double) 1, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
		else {
			super.updatePassengerPosition(passenger);
		}
	}

	@Override
	public double getMountedHeightOffset() {
		return 0.25f;
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
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(new AnimationBuilder().loop("zomboni.walking"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(new AnimationBuilder().loop("zomboni.walking"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("zomboni.idle"));
			}
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}
        return PlayState.CONTINUE;
    }


	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		createPassenger();
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public void createPassenger() {
		if (world instanceof ServerWorld serverWorld) {
			ZomboniEntity zomboniEntity = new ZomboniEntity(PvZEntity.ZOMBONI, this.world);
			zomboniEntity.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			zomboniEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			zomboniEntity.startRiding(this);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		this.world.sendEntityStatus(this, (byte) 106);
		this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 1F, 1F);
		super.onDeath(source);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createZomboniVehicleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.zomboniVH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
	}
}
