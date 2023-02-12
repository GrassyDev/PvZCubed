package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BerserkerGearEntityModel extends AnimatedGeoModel<BerserkerGearEntity> {

	@Override
	public Identifier getModelResource(BerserkerGearEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(BerserkerGearEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(BerserkerGearEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
