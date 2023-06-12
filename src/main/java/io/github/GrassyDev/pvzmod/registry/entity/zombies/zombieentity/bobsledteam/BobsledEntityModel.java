package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bobsledteam;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BobsledEntityModel extends AnimatedGeoModel<BobsledRiderEntity> {

	@Override
	public Identifier getModelResource(BobsledRiderEntity object)
	{
		return new Identifier("pvzmod", "geo/bobsledrider.geo.json");
	}

	@Override
	public Identifier getTextureResource(BobsledRiderEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bobsled/bobsledrider.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bobsled/bobsledrider_dmg1.png");
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(BobsledRiderEntity object)
	{
		return new Identifier ("pvzmod", "animations/newbrowncoat.json");
	}
}
