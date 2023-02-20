package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefensiveEndVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.BucketheadGearEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

public class DefensiveEndEntity extends GargantuarEntity implements IAnimatable {
	public DefensiveEndEntity(EntityType<? extends GargantuarEntity> entityType, World world) {
		super(entityType, world);
		this.healthImp = 360;
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


	@Override
	public void tryLaunch(Entity target) {
		super.tryLaunch(target);
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(DefensiveEndEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		double random = Math.random();
		if (random <= 0.75){
			setVariant(DefensiveEndVariants.DEFAULT);
		}
		else {
			setVariant(DefensiveEndVariants.NEWYEARIMP);
		}
		this.createProp();
		if (this.getVariant().equals(DefensiveEndVariants.NEWYEARIMP)){
			this.impEntity = new SuperFanImpEntity(PvZEntity.NEWYEARIMP, this.world);
		}
		else {
			this.impEntity = new SuperFanImpEntity(PvZEntity.SUPERFANIMP, this.world);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DefensiveEndVariants getVariant() {
		return DefensiveEndVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DefensiveEndVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DEFENSIVEENDEGG.getDefaultStack();
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public void createProp(){
		BucketheadGearEntity propentity = new BucketheadGearEntity(PvZEntity.DEFENSIVEENDGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}
}
