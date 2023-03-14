package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

public abstract class PvZombieEntity extends GeneralPvZombieEntity implements Monster {

	/** For Hypnotized Zombies **/
	public boolean invulnerableZombie;
	protected PvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	public void dismountVehicle() {
		super.dismountVehicle();
	}

	@Override
	protected void updateDespawnCounter() {
	}
}
