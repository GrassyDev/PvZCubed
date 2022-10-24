package io.github.GrassyDev.pvzmod.registry.plants;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class EnlightenEntity extends GolemEntity {
	/** Plants:
	 *   - Sunflower
	 *   - Sun-shroom
	 * **/
	protected EnlightenEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
