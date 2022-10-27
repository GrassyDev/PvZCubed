package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class AilmentEntity extends PlantEntity {
	/** Plants:
	 *   - Puff-shroom
	 *   - Scaredy-shroom
	 *   - Fume-shroom
	 * **/
	protected AilmentEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
