package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BucketheadGearEntityModel extends AnimatedGeoModel<BucketheadGearEntity> {

	@Override
	public Identifier getModelResource(BucketheadGearEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(BucketheadGearEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(BucketheadGearEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
