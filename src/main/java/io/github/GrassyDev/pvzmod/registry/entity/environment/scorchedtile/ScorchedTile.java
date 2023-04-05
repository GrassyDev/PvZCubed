package io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ScorchedTile extends TileEntity {
	public ScorchedTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 1200){
			this.discard();
		}
	}
}
