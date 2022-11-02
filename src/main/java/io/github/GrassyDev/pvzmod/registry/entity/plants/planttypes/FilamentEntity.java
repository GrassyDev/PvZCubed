package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class FilamentEntity extends PlantEntity {
	/** Plants:
	 *   - N/A
	 * **/
	protected FilamentEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
