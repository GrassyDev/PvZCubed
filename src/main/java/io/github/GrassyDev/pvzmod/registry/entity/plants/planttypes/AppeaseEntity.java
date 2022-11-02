package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class AppeaseEntity extends PlantEntity {
	/** Plants:
	 *   - Peashooter
	 *   - Repeater
	 *   - Threepeater
	 * **/
	protected AppeaseEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
