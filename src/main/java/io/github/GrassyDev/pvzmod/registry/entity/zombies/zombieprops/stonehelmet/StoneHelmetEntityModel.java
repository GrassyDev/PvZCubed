package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StoneHelmetEntityModel extends AnimatedGeoModel<StoneHelmetEntity> {

	@Override
	public Identifier getModelResource(StoneHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(StoneHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(StoneHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
