package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

public class DefensiveEndEntity extends GargantuarEntity implements IAnimatable {
	public DefensiveEndEntity(EntityType<? extends GargantuarEntity> entityType, World world) {
		super(entityType, world);
		this.impEntity = new SuperFanImpEntity(PvZEntity.SUPERFANIMP, this.world);
		this.healthImp = 360;
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DEFENSIVEENDEGG.getDefaultStack();
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/



	public void createProp(){
		DefensiveEndGearEntity propentity = new DefensiveEndGearEntity(PvZEntity.DEFENSIVEENDGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}
}
