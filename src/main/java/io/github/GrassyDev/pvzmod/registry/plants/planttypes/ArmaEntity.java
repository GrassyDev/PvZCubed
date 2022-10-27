package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class ArmaEntity extends PlantEntity {
	/** Plants:
	 *   - N/A
	 * **/
	protected ArmaEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
