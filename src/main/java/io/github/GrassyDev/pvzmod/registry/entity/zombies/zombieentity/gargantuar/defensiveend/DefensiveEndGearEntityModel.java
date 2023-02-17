package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DefensiveEndGearEntityModel extends AnimatedGeoModel<DefensiveEndGearEntity> {

	@Override
	public Identifier getModelResource(DefensiveEndGearEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(DefensiveEndGearEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(DefensiveEndGearEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
