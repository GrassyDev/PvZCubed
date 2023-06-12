package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SnowTile extends TileEntity {

	public SnowTile(EntityType<? extends SnowTile> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.setInvulnerable(true);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 2400){
			this.discard();
		}
	}
}
