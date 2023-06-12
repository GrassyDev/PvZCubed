package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MetalVehicleEntityModel extends AnimatedGeoModel<MetalVehicleEntity> {

	@Override
	public Identifier getModelResource(MetalVehicleEntity object)
	{
		if (object.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			return new Identifier("pvzmod", "geo/zomboni.geo.json");
		}
		else {
			return new Identifier("pvzmod", "geo/bobsled.geo.json");
		}
	}

	@Override
	public Identifier getTextureResource(MetalVehicleEntity object)
	{
		Identifier identifier = new Identifier("pvzmod", "textures/entity/zomboni/zomboni.png");
		if (object.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			if (object.hasPassengers()) {
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/zomboni/zomboni_dmg.png");
				}
			} else {
				identifier = new Identifier("pvzmod", "textures/entity/zomboni/zomboni_riderless.png");
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/zomboni/zomboni_dmg_riderless.png");
				}
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/bobsled/bobsled_riderless.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(MetalVehicleEntity object)
	{
		if (object.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			return new Identifier("pvzmod", "animations/zomboni.json");
		}
		else {
			return new Identifier("pvzmod", "animations/bobsled.json");
		}
	}
}
