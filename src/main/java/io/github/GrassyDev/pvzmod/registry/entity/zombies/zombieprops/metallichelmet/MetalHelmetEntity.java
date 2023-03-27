package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.gears.MetallicHelmetVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
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

public class MetalHelmetEntity extends ZombiePropEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public MetalHelmetEntity(EntityType<? extends MetalHelmetEntity> entityType, World world) {
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
			DataTracker.registerData(MetalHelmetEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.BUCKETGEAR)){
			setVariant(MetallicHelmetVariants.BUCKET);
		}
		else if (this.getType().equals(PvZEntity.MEDALLIONGEAR)){
			setVariant(MetallicHelmetVariants.MEDALLION);
		}
		else if (this.getType().equals(PvZEntity.FOOTBALLGEAR)){
			setVariant(MetallicHelmetVariants.FOOTBALL);
		}
		else if (this.getType().equals(PvZEntity.BERSERKERGEAR)){
			setVariant(MetallicHelmetVariants.BERSERKER);
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEENDGEAR)){
			setVariant(MetallicHelmetVariants.DEFENSIVEEND);
		}
		else if (this.getType().equals(PvZEntity.BLASTRONAUTGEAR)){
			setVariant(MetallicHelmetVariants.BLASTRONAUT);
		}
		if (this.getType().equals(PvZEntity.KNIGHTGEAR)){
			setVariant(MetallicHelmetVariants.KNIGHT);
		}
		else {
			setVariant(MetallicHelmetVariants.BUCKET);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public MetallicHelmetVariants getVariant() {
		return MetallicHelmetVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(MetallicHelmetVariants variant) {
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

	public static DefaultAttributeContainer.Builder createBucketGearAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.bucketH());
    }

	public static DefaultAttributeContainer.Builder createMedallionGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.medallionH());
	}

	public static DefaultAttributeContainer.Builder createFootballGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.footballHelmH());
	}

	public static DefaultAttributeContainer.Builder createBerserkerGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.berserkerHelmH());
	}

	public static DefaultAttributeContainer.Builder createDefensiveEndGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.defensiveendHelmH());
	}

	public static DefaultAttributeContainer.Builder createBlastronautGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.blastronautHelmH());
	}

	public static DefaultAttributeContainer.Builder createKnightGearAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.knightHelmH());
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZCubed.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.BUCKETGEAR)){
			itemStack = ModItems.BUCKETHEADEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.MEDALLIONGEAR)){
			itemStack = ModItems.BACKUPDANCEREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.FOOTBALLGEAR)){
			itemStack = ModItems.FOOTBALLEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.BERSERKERGEAR)){
			itemStack = ModItems.BERSERKEREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEENDGEAR)){
			itemStack = ModItems.DEFENSIVEENDEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.BLASTRONAUTGEAR)){
			itemStack = ModItems.BLASTRONAUTEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.KNIGHTGEAR)){
			itemStack = ModItems.PEASANTKNIGHTEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.BUCKETHEADEGG.getDefaultStack();
		}
		return itemStack;
	}

}
