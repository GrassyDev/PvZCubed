package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasticHelmetEntityModel extends AnimatedGeoModel<PlasticHelmetEntity> {

	@Override
	public Identifier getModelResource(PlasticHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasticHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(PlasticHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
