package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class EnlightenEntity extends PlantEntity {
	/** Plants:
	 *   - Sunflower
	 *   - Sun-shroom
	 * **/
	protected EnlightenEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
