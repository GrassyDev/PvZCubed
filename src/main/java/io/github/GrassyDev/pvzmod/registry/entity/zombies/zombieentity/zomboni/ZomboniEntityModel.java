package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zomboni;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZomboniEntityModel extends AnimatedGeoModel<ZomboniEntity> {

	@Override
	public Identifier getModelResource(ZomboniEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(ZomboniEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/zomboni/zomboni.png");
	}

	@Override
	public Identifier getAnimationResource(ZomboniEntity object)
	{
		return new Identifier ("pvzmod", "animations/zomboni.json");
	}
}
