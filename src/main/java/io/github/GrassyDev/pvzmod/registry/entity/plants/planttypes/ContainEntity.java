package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class ContainEntity extends PlantEntity {
	/** Plants:
	 *   - Grave Buster
	 * **/
	protected ContainEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
