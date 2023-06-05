package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.variants.gears.PlasticHelmetVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PlasticHelmetEntity extends ZombiePropEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public PlasticHelmetEntity(EntityType<? extends PlasticHelmetEntity> entityType, World world) {
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

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(PlasticHelmetEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.CONEHEADGEAR)){
			setVariant(PlasticHelmetVariants.CONE);
		}
		else {
			setVariant(PlasticHelmetVariants.CONE);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public PlasticHelmetVariants getVariant() {
		return PlasticHelmetVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(PlasticHelmetVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		if (this.getVehicle() == null){
			this.kill();
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
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createConeheadGearAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.coneH());
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
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.CONEHEAD)){
			itemStack = ModItems.CONEHEADEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.CONEHEADEGG.getDefaultStack();
		}
		return itemStack;
	}

}
