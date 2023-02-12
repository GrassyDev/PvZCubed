package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ConeheadGearEntityModel extends AnimatedGeoModel<ConeheadGearEntity> {

	@Override
	public Identifier getModelResource(ConeheadGearEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(ConeheadGearEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(ConeheadGearEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
