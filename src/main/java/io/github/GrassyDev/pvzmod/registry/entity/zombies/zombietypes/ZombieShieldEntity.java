package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public abstract class ZombieShieldEntity extends ZombiePropEntity{
	protected ZombieShieldEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
}
