package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class EnforceEntity extends PlantEntity {
	/** Plants:
	 *   - Chomper
	 * **/
	protected EnforceEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
