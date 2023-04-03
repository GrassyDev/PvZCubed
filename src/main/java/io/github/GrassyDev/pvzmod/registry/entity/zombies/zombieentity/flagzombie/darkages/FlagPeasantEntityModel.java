package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.darkages;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagPeasantEntityModel extends AnimatedGeoModel<FlagPeasantEntity> {

	@Override
	public Identifier getModelResource(FlagPeasantEntity object)
	{
		return new Identifier("pvzmod", "geo/flagpeasant.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlagPeasantEntity object)
	{
		Identifier identifier;
		if (object.getHypno()) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_hypnotized.png");
			if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_dmg1_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant.png");
			if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_dmg1.png");
			}
		}
		return identifier;
	}

	@Override
	public Identifier getAnimationResource(FlagPeasantEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
