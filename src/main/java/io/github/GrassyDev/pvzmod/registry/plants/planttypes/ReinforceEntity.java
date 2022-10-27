package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class ReinforceEntity extends PlantEntity {
	/** Plants:
	 *   - Wall-nut
	 * **/
	protected ReinforceEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
