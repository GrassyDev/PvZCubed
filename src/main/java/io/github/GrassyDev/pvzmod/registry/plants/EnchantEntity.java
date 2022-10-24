package io.github.GrassyDev.pvzmod.registry.plants;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class EnchantEntity extends GolemEntity {
	/** Plants:
	 *   - Hypno-shroom
	 * **/
	protected EnchantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
