package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FootballGearEntityModel extends AnimatedGeoModel<FootballGearEntity> {

	@Override
	public Identifier getModelResource(FootballGearEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(FootballGearEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(FootballGearEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
