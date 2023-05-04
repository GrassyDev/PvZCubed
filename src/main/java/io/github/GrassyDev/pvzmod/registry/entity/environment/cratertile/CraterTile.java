package io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.List;

public class CraterTile extends TileEntity {
	public CraterTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 2400){
			this.discard();
		}
		if (this.age > 5) {
			List<TileEntity> list = world.getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
			for (TileEntity tileEntity : list) {
				if (tileEntity != this) {
					tileEntity.discard();
				}
			}
		}
	}
}
