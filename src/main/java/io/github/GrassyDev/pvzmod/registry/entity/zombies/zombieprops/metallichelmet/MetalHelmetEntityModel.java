package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MetalHelmetEntityModel extends AnimatedGeoModel<MetalHelmetEntity> {

	@Override
	public Identifier getModelResource(MetalHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(MetalHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(MetalHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
