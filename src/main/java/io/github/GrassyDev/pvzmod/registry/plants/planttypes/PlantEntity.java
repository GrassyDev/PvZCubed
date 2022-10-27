package io.github.GrassyDev.pvzmod.registry.plants.planttypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.doomshroom.DoomshroomEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public abstract class PlantEntity extends GolemEntity {
	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
}
